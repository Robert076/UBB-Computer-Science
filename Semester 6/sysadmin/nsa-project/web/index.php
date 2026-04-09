<?php
class RedisSessionHandler implements SessionHandlerInterface {
    private $redis;
    private $ttl = 3600;

    public function open($path, $name): bool {
        $this->redis = new Redis();
        $this->redis->connect(getenv('REDIS_HOST'), 6379);
        return true;
    }
    public function close(): bool { return true; }
    public function read($id): string {
        return $this->redis->get("sess:$id") ?: '';
    }
    public function write($id, $data): bool {
        return $this->redis->setex("sess:$id", $this->ttl, $data);
    }
    public function destroy($id): bool {
        $this->redis->del("sess:$id");
        return true;
    }
    public function gc($max): int { return 0; }
}

session_set_save_handler(new RedisSessionHandler(), true);
session_start();

$host = getenv('DB_HOST');
$db   = getenv('DB_NAME');
$user = getenv('DB_USER');
$pass = getenv('DB_PASS');
$mailHost = getenv('MAIL_HOST');
$mailPort = getenv('MAIL_PORT');

$conn = new PDO("mysql:host=$host;dbname=$db", $user, $pass);
$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$conn->exec("CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    confirmed TINYINT(1) DEFAULT 0,
    token VARCHAR(255)
)");

$conn->exec("CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)");

$message = '';

if (isset($_POST['register'])) {
    $email = $_POST['email'];
    $password = password_hash($_POST['password'], PASSWORD_BCRYPT);
    $token = bin2hex(random_bytes(16));

    try {
        $stmt = $conn->prepare("INSERT INTO users (email, password, token) VALUES (?, ?, ?)");
        $stmt->execute([$email, $password, $token]);

        $socket = fsockopen($mailHost, $mailPort);
        fputs($socket, "EHLO localhost\r\n");
        fputs($socket, "MAIL FROM: <noreply@nsa.robertberes.com>\r\n");
        fputs($socket, "RCPT TO: <$email>\r\n");
        fputs($socket, "DATA\r\n");
        fputs($socket, "Subject: Confirm your registration\r\n");
        fputs($socket, "To: $email\r\n");
        fputs($socket, "\r\n");
        fputs($socket, "Click to confirm: https://nsa.robert-beres.com:8443?token=$token\r\n");
        fputs($socket, ".\r\n");
        fputs($socket, "QUIT\r\n");
        fclose($socket);

        $message = "Registered! Check MailHog for confirmation email.";
    } catch (Exception $e) {
        $message = "Email already exists.";
    }
}

if (isset($_GET['token'])) {
    $stmt = $conn->prepare("UPDATE users SET confirmed=1, token=NULL WHERE token=?");
    $stmt->execute([$_GET['token']]);
    $message = "Email confirmed! You can now log in.";
}

if (isset($_POST['login'])) {
    $stmt = $conn->prepare("SELECT * FROM users WHERE email=?");
    $stmt->execute([$_POST['email']]);
    $dbUser = $stmt->fetch();

    if ($dbUser && password_verify($_POST['password'], $dbUser['password'])) {
        if (!$dbUser['confirmed']) {
            $message = "Please confirm your email first.";
        } else {
            $_SESSION['user'] = $dbUser['email'];
            $message = "Welcome, " . $dbUser['email'];
        }
    } else {
        $message = "Invalid credentials.";
    }
}

if (isset($_GET['logout'])) {
    session_destroy();
    header('Location: /');
    exit;
}

if (isset($_SESSION['user'])) {
    if (isset($_POST['create'])) {
        $stmt = $conn->prepare("INSERT INTO items (name) VALUES (?)");
        $stmt->execute([$_POST['name']]);
    } elseif (isset($_POST['delete'])) {
        $stmt = $conn->prepare("DELETE FROM items WHERE id=?");
        $stmt->execute([$_POST['id']]);
    } elseif (isset($_POST['update'])) {
        $stmt = $conn->prepare("UPDATE items SET name=? WHERE id=?");
        $stmt->execute([$_POST['name'], $_POST['id']]);
    }
}

$items = $conn->query("SELECT * FROM items")->fetchAll();
$ip = $_SERVER['SERVER_ADDR'];
?>

<!DOCTYPE html>
<html>
<head><title>NSA Project</title></head>
<body>
    <h2>Server IP: <?= $ip ?></h2>

    <?php if ($message): ?>
        <p style="color:green"><?= $message ?></p>
    <?php endif; ?>

    <?php if (!isset($_SESSION['user'])): ?>
        <h3>Register</h3>
        <form method="POST">
            <input name="email" type="email" placeholder="Email" required/>
            <input name="password" type="password" placeholder="Password" required/>
            <button name="register">Register</button>
        </form>

        <h3>Login</h3>
        <form method="POST">
            <input name="email" type="email" placeholder="Email" required/>
            <input name="password" type="password" placeholder="Password" required/>
            <button name="login">Login</button>
        </form>

    <?php else: ?>
        <p>Logged in as <?= $_SESSION['user'] ?> | <a href="?logout">Logout</a></p>

        <h3>Add Item</h3>
        <form method="POST">
            <input name="name" placeholder="Item name" required/>
            <button name="create">Add</button>
        </form>

        <h3>Items</h3>
        <table border="1">
            <tr><th>ID</th><th>Name</th><th>Actions</th></tr>
            <?php foreach ($items as $item): ?>
            <tr>
                <td><?= $item['id'] ?></td>
                <td>
                    <form method="POST" style="display:inline">
                        <input name="name" value="<?= $item['name'] ?>"/>
                        <input type="hidden" name="id" value="<?= $item['id'] ?>"/>
                        <button name="update">Update</button>
                    </form>
                </td>
                <td>
                    <form method="POST" style="display:inline">
                        <input type="hidden" name="id" value="<?= $item['id'] ?>"/>
                        <button name="delete">Delete</button>
                    </form>
                </td>
            </tr>
            <?php endforeach; ?>
        </table>
    <?php endif; ?>
</body>
</html>
