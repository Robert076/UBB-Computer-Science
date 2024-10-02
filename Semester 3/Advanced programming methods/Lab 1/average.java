public class average {
    public static void main(String[] args) {
        int sum = 0;
        for (String a : args)
            sum += Integer.parseInt(a);
        if (args.length != 0)
            System.out.println(sum / args.length);
        else
            System.out.println("Nu ai argumente baa Diddy");
    }
}