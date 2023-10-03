package search;

public class BinarySearch {
    // POST: выводит в консоль минимальное значение индекса i, при котором a[i] <= x
    public static void main(String[] args) {
        /*
            PRE: args != null && args.length > 0 &&
            Для любого целочисленного i : 0 <= i <= args.length - 1  ->  args[i] != null &&
            Для любого целочисленного i : 1 <= i <= args.length - 2  ->  args[i] >= args[i + 1] &&
            Для любого целочисленного i : 0 <= i <= args.length - 1  -> args[i] - число типа Integer
        */
        int x = Integer.parseInt(args[0]);
        // x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        // x = Integer.parseInt(args[0]) && a = int[args.length - 1]
        int i = 1;
        // x = Integer.parseInt(args[0]) && a = int[args.length - 1] && i = 0
        // i >= 0
        while (i < args.length) {
            // i >= 0
            a[i - 1] = Integer.parseInt(args[i]);
            // i >= 0 && a[i - 1] = Integer.parseInt(args[i])
            i += 1;
            // i >= 0 && a[i - 2] = Integer.parseInt(args[i - 1])
        }
        /*
            a != null && x != null &&
            Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1]
        */
        int recursiveAnswer = recursiveBinarySearch(x, a);
        /*
            a != null && x != null &&
            Для любого целочисленного i : 0 <= i <= recursiveAnswer - 1  ->  a[i] > x &&
            Для любого целочисленного i : recursiveAnswer <= i <= a.length - 1  -> a[i] <= x
        */
        /*
            a != null && x != null &&
            Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1]
        */
        int iterativeAnswer = iterativeBinarySearch(x, a);
        /*
            a != null && x != null &&
            Для любого целочисленного i : 0 <= i <= recursiveAnswer - 1  ->  a[i] > x &&
            Для любого целочисленного i : recursiveAnswer <= i <= a.length - 1  -> a[i] <= x
        */
        // recursiveAnswer != null && iterativeAnswer != null && recursiveAnswer == iterativeAnswer
        System.out.println(recursiveAnswer);
        // Вывод результата в консоль
    }
    /*
        POST: a != null && x != null &&
        Для любого целочисленного i : 0 <= i <= l - 1  ->  a[i] > x &&
        Для любого целочисленного i : l <= i <= a.length - 1  -> a[i] <= x
    */
    public static int iterativeBinarySearch(int x, int[] a) {
        /*
            PRE: a != null && x != null &&
            Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1]
        */
        int l = 0;
        // Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1] && l == 0
        int r = a.length;
        // Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1] && l == 0 && r == a.length
        // 0 <= l <= a.length && 0 <= r <= a.length
        while (l < r) {
            // 0 <= l <= a.length && 0 <= r <= a.length && l < r
            int m;
            if ((l + r)%2 == 1) {
                // (l + r)%2 == 1
                m = (l + r - 1) / 2;
                // (l + r)%2 == 1 && m = (l + r - 1) / 2 && l <= m < r
            } else {
                // (l + r)%2 != 1
                m = (l + r) / 2;
                // (l + r)%2 != 1 && m = (l + r)/2 && l <= m < r
            }

            if (a[m] > x) {
                // a[m] > x
                l = m + 1;
                // a[l - 1] > x && l = m + 1
            } else {
                // a[m] <= x
                r = m;
                // a[r] <= x && r = m
            }
            // a[r] <= x && a[l - 1] > x
        }
        /*
            Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1]
            Для любого целочисленного i : 0 <= i <= l - 1  ->  a[i] > x &&
            Для любого целочисленного i : l <= i <= a.length - 1  -> a[i] <= x
        */
        return l;
    }
    /*
        POST: a != null && x != null &&
        Для любого целочисленного i : 0 <= i <= recursiveAnswer - 1  ->  a[i] > x &&
        Для любого целочисленного i : recursiveAnswer <= i <= a.length - 1  -> a[i] <= x
    */
    public static int recursiveBinarySearch(int x, int[] a, int l, int r) {
        /*
            PRE: a != null && x != null && 0 <= l <= a.length && 0 <= r <= a.length && l <= r && a[r] <= x && a[l - 1] > x
            Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1]
        */
        if (l == r) {
            // Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1] && a[l] >= x > a[r]
            return l;

        }
        int m;
        if ((l + r)%2 == 1) {
            // (l + r)%2 == 1
            m = (l + r - 1) / 2;
            // (l + r)%2 == 1 && m = (l + r - 1) / 2 && l <= m < r
        } else {
            // (l + r)%2 != 1
            m = (l + r) / 2;
            // (l + r)%2 != 1 && m = (l + r)/2 && l <= m < r
        }

        if (a[m] > x) {
            // a[m] > x && a[r] <= x
            return recursiveBinarySearch(x, a, m + 1, r);
        } else {
            //a[m] <= x && a[l - 1] > x
            return recursiveBinarySearch(x, a, l, m);
        }
    }
    /*
        POST: a != null && x != null &&
        Для любого целочисленного i : 0 <= i <= recursiveAnswer - 1  ->  a[i] > x &&
        Для любого целочисленного i : recursiveAnswer <= i <= a.length - 1  -> a[i] <= x
    */
    public static int recursiveBinarySearch(int x, int[] a) {
        /*
            PRE: a != null && x != null &&
            Для любого целочисленного i : 0 <= i <= a.length - 2  ->  a[i] >= a[i + 1]
        */
        return recursiveBinarySearch(x, a, 0, a.length);
    }
}
