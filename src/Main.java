import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        char[] source = readAndGetArr("1.txt");
        char[] permutedSource = permutation(source,"order.txt", 64);
        char[] startKey = startKey(readAndGetArr("key.txt"));
        char[] bigKey = workWithKey(readAndGetArr("key.txt"));
        char[] expandedR = expandArr(getHalf(permutedSource, false), "e_ras.txt" , 48);
        char[] RKey = getC0(startKey);
        char[] LKey = getD0(startKey);
        char[] shiftedRKey = shiftArr(RKey,1);
        char[] shiftedLKey = shiftArr(LKey,1);
        char[] LRKey = concArr(shiftedRKey, shiftedLKey);
        char[] roundKey = getResKey(LRKey);
        char[] firstRes = logicalXOR(expandedR, roundKey);
        System.out.println("res");
        writeToFile(permutedSource, "res.txt");

    }

    private static char[] readAndGetArr(String filePath) throws IOException {
        File f = new File(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String a = br.readLine();
        return a.toCharArray();
    }

    private static char[] permutation(char[] source, String orderFile, int size) throws IOException {

        char[] result = new char[size];
        File f = new File(orderFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String a = br.readLine();
        String[] l = a.split("\\s+");
        for (int i = 0; i < size; i++) {
            int orderNum = Integer.parseInt(l[i]);
            if (orderNum < source.length){
                result[i] = source[Integer.parseInt(l[i]) - 1];
            }
        }
        return result;
    }

    private static void writeToFile(char[] arr, String filePath) throws IOException {
        File file = new File(filePath);
        OutputStreamWriter f = new FileWriter(file);
        try {
            f.write(arr,0, arr.length);
        }
        catch (IOException e) {
            try {f.close();} catch (Exception e1) {};
            return;
        }
        f.close();
    }

    private static char[] expandArr(char[] source, String expPath, int resSize) {
        char[] res = new char[resSize];
        try {
            res = permutation(source, expPath, resSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static char[] getHalf(char[] source, boolean isLeftHalf) {
        char[] R0 = new char[32];
        int offset = source.length/2;
        if (isLeftHalf)
            offset = 0;
        for (int i = 0; i < source.length/2; i++)
        {
            R0[i] = source[offset - 1 + i];
        }
        return R0;
    }

    private static char[] workWithKey(char[] input) {
//        key_permutation
        // input = 56
        char[] add_key = new char[64];
        int temp = 0;
        int counter = 0;
        for (int i = 0; i < add_key.length; i++)
        {
            if ( i > 1 && (i+1)%8 == 0){
                add_key[i] = Character.forDigit(temp,10);
                temp = 0;
            } else {
                add_key[i] = input[counter];
                temp = logicalXOR(temp, input[counter]);
                counter++;
            }
        }
        return add_key;
    }

    private static char[] startKey(char[] inputKey)
    {
        char[] res = new char[56];
        char[] res1 = new char[64];
        int deleted_num=0;
        for (int i = 0; i < inputKey.length; i++)
        {
            res1[i] = inputKey[i];
        }
        for (int i = 0; i < inputKey.length; i++)
        {
            if ( i>1 && (i+1)%8 == 0) {
                for (int j = i; j < res1.length - deleted_num - 1; j++) {
                    res1[j] = res1[j + 1];
                }
                int a = res1.length - 1 - deleted_num;
                res1[a] = '0';
                deleted_num++;
            }
        }
        System.out.println("lol");

        for (int i = 0; i < res.length; i++)
        {
            res[i] = res1[i];
        }
        return res;
    }

    private static char[] getC0(char[] input64) throws IOException {
        return permutation(input64, "key_permutation_c.txt", 28);
    }
    private static char[] getD0(char[] input64) throws IOException {
        return permutation(input64, "key_permutation_d.txt", 28);
    }

    private static int logicalXOR(int a , char b) {
        if (a == 0){
            if (b == '0')
            {
                return 0;
            }
            else {
                return 1;
            }
        } else {
            if (b == '1')
            {
                return 0;
            } else
                return 1;
        }
    }

    private static char[] logicalXOR(char[] a, char[] b) {
        if (a.length != b.length)
        {
            System.out.println("Error while XOR, arrays have not same size");
            return null;
        }

        char[] c = new char[a.length];
        for (int i = 0; i < a.length; i++) {
            if (a[i] == '0'){
                if (b[i] == '0')
                {
                    c[i] = '0';
                }
                else {
                    c[i] = '1';
                }
            } else {
                if (b[i] == '1')
                {
                    c[i] = '0';
                } else
                    c[i] = '1';
            }
        }
        return c;
    }

    private static char[] concArr(char[] a, char[] b)
    {
        StringBuilder sb = new StringBuilder(a.length + b.length);
        sb.append(a);
        sb.append(b);

        return sb.toString().toCharArray();
    }

    private static char[] shiftArr (char[] a , int shift)
    {
        a[a.length - 1] = a[0];
        while (shift > 0)
        {
            for (int i = 0; i < a.length - 1; i++)
            {
                a[i] = a[i+1];
            }
            shift--;
        }
        return a;
    }

    private static char[] getResKey(char[] a) throws IOException {
        return permutation(a, "key_permutation.txt", 48);
    }

    private static void sPerm(char[] a)
    {
        char[] c = a.clone();
        int permNum = 0;
        for (int i = 0; i < c.length; i = i + 8)
        {
            permNum++;
            char[] temp = new char[8];
            for (int j = i; j < i + 8; j++)
            {
                temp[0] = c[j];
            }
            // temp . s permutation
        }
    }
}
