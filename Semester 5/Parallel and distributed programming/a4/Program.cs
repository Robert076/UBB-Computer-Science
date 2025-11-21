using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Collections.Generic;

static class HttpHelpers
{
    public static byte[] BuildGetRequest(string host, string path)
    {
        string req =
            $"GET {path} HTTP/1.1\r\n" +
            $"Host: {host}\r\n" +
            $"Connection: close\r\n\r\n";
        return Encoding.ASCII.GetBytes(req);
    }
}

/// -------------------------------------------------------------
///  1) CALLBACK VERSION
/// -------------------------------------------------------------
class CallbackHttpDownloader
{
    private Socket socket;
    private byte[] buffer = new byte[4096];
    private List<byte> received = new List<byte>();
    private string host;
    private string path;
    private TaskCompletionSource<byte[]> tcs;

    public Task<byte[]> Run(string host, string path)
    {
        this.host = host;
        this.path = path;
        tcs = new TaskCompletionSource<byte[]>();

        socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        socket.BeginConnect(host, 80, OnConnected, null);

        return tcs.Task;
    }

    void OnConnected(IAsyncResult ar)
    {
        socket.EndConnect(ar);
        byte[] req = HttpHelpers.BuildGetRequest(host, path);
        socket.BeginSend(req, 0, req.Length, SocketFlags.None, OnSent, null);
    }

    void OnSent(IAsyncResult ar)
    {
        socket.EndSend(ar);
        socket.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, OnReceived, null);
    }

    void OnReceived(IAsyncResult ar)
    {
        int bytes = socket.EndReceive(ar);
        if (bytes == 0)
        {
            socket.Close();
            tcs.TrySetResult(received.ToArray());
            return;
        }

        for (int i = 0; i < bytes; i++)
            received.Add(buffer[i]);

        socket.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, OnReceived, null);
    }
}

/// -------------------------------------------------------------
///  2) CONTINUEWITH VERSION
/// -------------------------------------------------------------
static class SocketTaskExtensions
{
    public static Task ConnectAsync(this Socket s, string host, int port)
    {
        var tcs = new TaskCompletionSource<bool>();
        s.BeginConnect(host, port, ar =>
        {
            try { s.EndConnect(ar); tcs.SetResult(true); }
            catch (Exception ex) { tcs.SetException(ex); }
        }, null);
        return tcs.Task;
    }

    public static Task<int> SendAsync(this Socket s, byte[] buffer)
    {
        var tcs = new TaskCompletionSource<int>();
        s.BeginSend(buffer, 0, buffer.Length, SocketFlags.None, ar =>
        {
            try { tcs.SetResult(s.EndSend(ar)); }
            catch (Exception ex) { tcs.SetException(ex); }
        }, null);
        return tcs.Task;
    }

    public static Task<int> ReceiveAsync(this Socket s, byte[] buffer)
    {
        var tcs = new TaskCompletionSource<int>();
        s.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, ar =>
        {
            try { tcs.SetResult(s.EndReceive(ar)); }
            catch (Exception ex) { tcs.SetException(ex); }
        }, null);
        return tcs.Task;
    }
}

class ContinueWithDownloader
{
    private Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
    private byte[] buffer = new byte[4096];
    private List<byte> received = new();

    public Task<byte[]> Run(string host, string path)
    {
        return socket.ConnectAsync(host, 80)
            .ContinueWith(_ =>
            {
                byte[] req = HttpHelpers.BuildGetRequest(host, path);
                return socket.SendAsync(req);
            }).Unwrap()
            .ContinueWith(_ => ReadLoop())
            .Unwrap();
    }

    Task<byte[]> ReadLoop()
    {
        var tcs = new TaskCompletionSource<byte[]>();

        void Loop()
        {
            socket.ReceiveAsync(buffer).ContinueWith(tr =>
            {
                int n = tr.Result;
                if (n == 0)
                {
                    socket.Close();
                    tcs.SetResult(received.ToArray());
                    return;
                }

                for (int i = 0; i < n; i++) received.Add(buffer[i]);
                Loop();
            });
        }

        Loop();
        return tcs.Task;
    }
}

/// -------------------------------------------------------------
///  3) ASYNC/AWAIT VERSION
/// -------------------------------------------------------------
class AsyncAwaitDownloader
{
    public async Task<byte[]> Run(string host, string path)
    {
        var socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        await socket.ConnectAsync(host, 80);

        byte[] request = HttpHelpers.BuildGetRequest(host, path);
        await socket.SendAsync(request);

        List<byte> received = new();
        byte[] buf = new byte[4096];

        while (true)
        {
            int n = await socket.ReceiveAsync(buf);
            if (n == 0) break;
            for (int i = 0; i < n; i++)
                received.Add(buf[i]);
        }

        socket.Close();
        return received.ToArray();
    }
}

/// -------------------------------------------------------------
///  MAIN: choose implementation + download N files
/// -------------------------------------------------------------
class Program
{
    static async Task Main(string[] args)
    {
        if (args.Length < 2)
        {
            Console.WriteLine("Usage:\n dotnet run callbacks url...\n dotnet run continuewith url...\n dotnet run async url...");
            return;
        }

        string mode = args[0];
        string[] urls = args[1..];

        List<Task<byte[]>> tasks = new();

        foreach (var url in urls)
        {
            Uri u = new Uri(url);
            string host = u.Host;
            string path = u.PathAndQuery;

            if (mode == "callbacks")
            {
                var dl = new CallbackHttpDownloader();
                tasks.Add(dl.Run(host, path));
            }
            else if (mode == "continuewith")
            {
                var dl = new ContinueWithDownloader();
                tasks.Add(dl.Run(host, path));
            }
            else if (mode == "async")
            {
                var dl = new AsyncAwaitDownloader();
                tasks.Add(dl.Run(host, path));
            }
            else
            {
                Console.WriteLine("Unknown mode.");
                return;
            }
        }

        byte[][] results = await Task.WhenAll(tasks);

        for (int i = 0; i < urls.Length; i++)
        {
            Console.WriteLine($"Downloaded {results[i].Length} bytes from {urls[i]}");
        }
    }
}
