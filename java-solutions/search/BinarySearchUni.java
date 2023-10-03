package search;

public class BinarySearchUni {
    // POST: находит длинну возрастающего массива
    public static void main(String[] args) {
        /*
            PRE: args != null && args.length > 0 &&
            Для любого целочисленного i : 0 <= i <= args.length - 1  ->  args[i] != null &&
            Для любого целочисленного i : 0 <= i <= args.length - 1  -> args[i] - число типа Integer
            Существует такое x  ->  (Для любого целочисленного i : 1 <= i <= x - 1  ->  args[i] < args[i + 1]) &&
                (Для любого целочисленного i : x + 1 <= i <= args.length - 2  ->  args[i] > args[i + 1])
        */
        int[] a = new int[args.length];
        int sum = 0;
        // a = int[args.length - 1]
        int i = 0;
        // a = int[args.length - 1] && i = 0
        // i >= 0
        while (i < args.length) {
            // i >= 0
            a[i] = Integer.parseInt(args[i]);
            sum += a[i];
            // i >= 0 && a[i] = Integer.parseInt(args[i])
            i += 1;
            // i >= 0 && a[i - 1] = Integer.parseInt(args[i - 1])
        }
        System.out.println(sum%2 == 0 ? recursiveTernarySearch(a) : iterativeTernarySearch(a));
    }
    // PRE: минимальная длинна возрастающего массива
    private static int recursiveTernarySearch(int[] a) {
        // POST: a - 2 слитых в один массива, первый - строго возрастающий, второй строго убывает && a != null
        return recursiveTernarySearch(a, 0, a.length - 1);
    }
    // PRE: минимальная длинна возрастающего массива
    private static int recursiveTernarySearch(int[] a, int l, int r) {
        /*
        POST: a - 2 слитых в один массива, первый - строго возрастающий, второй строго убывает &&
        a != null && l != null && r != null && l >= 0 && r < a.length
         */
        if (r - l > 2) {
            // r - l > 2
            int m1 = l + (r - l)/3;
            // r - l > 2 && m1 = l + (r - l)/3
            int m2 = r - (r - l)/3;
            // r - l > 2 && m1 = l + (r - l)/3 && m2 = r - (r - l)/3
            if (a[m1] < a[m2]) {
                // a[m1] < a[m2]
                return recursiveTernarySearch(a, m1, r);
                /*
                Пояснение:
                Если m1 находится в возрастающем массиве и m2 находится в убывающем - для всех i меньших m1, a[i] будет меньше a[m1]
                Если m1 находится в возрастающем массиве и m2 в возрастающем - для всех i меньших m1, a[i] будет меньше a[m1],
                Если m1 в убывающем и m2 в убывающем - такого случая не может быть, так как по условию m1 < m2 и a[m1] > a[m2]
                 */
            } else {
                // a[m1] >= a[m2]
                return recursiveTernarySearch(a, l, m2);
                /*
                Пояснение:
                Если m1 находится в возрастающем массиве и m2 находится в убывающем - для всех i больших m2, a[i] будет меньше a[m2]
                Если m1 находится в возрастающем массиве и m2 в возрастающем - такого случая не может быть, так как по условию m1 < m2 b a[m1] < a[m2]
                Если m1 в убывающем и m2 в убывающем - для всех i больших m2, a[i] будет меньше a[m2]
                 */

            }
        } else {
            // r - l <= 2
            if (l < r && a[l] <= a[l + 1]) {
                // r - l <= 2 && l < r && a[l] <= a[l + 1]
                return recursiveTernarySearch(a, l + 1, r);
            } else {

                // r - l <= 2 && (l == r || a[l] > a[l + 1])
                return l;
                /*
                пояснение:
                Если a[l] == a[l+1], то l и l+1 принадлежат разным массивам, так как по условию первый сторого возрастает
                а второй строго убывает. Если a[l] < a[l+1], то a[l] принадлежит первому массиву.
                 */
            }
        }
    }
    // PRE: минимальная длинна возрастающего массива
    private static int iterativeTernarySearch (int[] a) {
        // POST: a - 2 слитых в один массива, первый - строго возрастающий, второй строго убывает && a != null
        int l = 0;
        // l = 0
        int r = a.length - 1;
        // l = 0 && r = a.length - 1
        while (r - l > 2) {
            // r - l > 2
            int m1 = l + (r - l)/3;
            // r - l > 2 && m1 = l + (r - l)/3
            int m2 = r - (r - l)/3;
            // r - l > 2 && m1 = l + (r - l)/3 && m2 = r - (r - l)/3
            if (a[m1] < a[m2]) {
                // a[m1] < a[m2]
                l = m1;
                // a[l] < a[m2] && l = m1
                /*
                Пояснение:
                Если m1 находится в возрастающем массиве и m2 находится в убывающем - для всех i меньших m1, a[i] будет меньше a[m1]
                Если m1 находится в возрастающем массиве и m2 в возрастающем - для всех i меньших m1, a[i] будет меньше a[m1],
                Если m1 в убывающем и m2 в убывающем - такого случая не может быть, так как по условию m1 < m2 и a[m1] > a[m2]
                 */
            } else {
                // a[m1] >= a[m2]
                r = m2;
                // a[m1] >= a[r] && r = m2
                /*
                Пояснение:
                Если m1 находится в возрастающем массиве и m2 находится в убывающем - для всех i больших m2, a[i] будет меньше a[m2]
                Если m1 находится в возрастающем массиве и m2 в возрастающем - такого случая не может быть, так как по условию m1 < m2 b a[m1] < a[m2]
                Если m1 в убывающем и m2 в убывающем - для всех i больших m2, a[i] будет меньше a[m2]
                 */
            }
        }
        // r - l <= 2
        while (l < r && a[l] <= a[l + 1]) {
            // l < r && a[l] <= a[l + 1]
            l++;
            // l <= r && a[l - 1] <= a[l]
            /*
                пояснение:
                Если a[l] == a[l+1], то l и l+1 принадлежат разным массивам, так как по условию первый сторого возрастает
                а второй строго убывает. Если a[l] < a[l+1], то a[l] принадлежит первому массиву.
                 */
        }
        return l;
    }
}