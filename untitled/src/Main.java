import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {

    private static final BigInteger pN = new BigInteger("7343259608467435220483315194643071998749291902377255584908507023494141040823509990331312479500111045677245170687831110406827061184738923109032612330815583");
    private static final BigInteger qN = new BigInteger("12185104106505185394305939763050014943895340619094571041786089760460678236509510548868423856860060969785581985436043232812567742023328265437875807700362601");
    public static final BigInteger N = pN.multiply(qN);
    private static final BigInteger fiN = pN.subtract(BigInteger.ONE).multiply(qN.subtract(BigInteger.ONE));
    public static Map<Integer, BigInteger> v;
    private static Map<Integer, BigInteger> vShift;
    public static final BigInteger ga = new BigInteger("3270499213");
    public static final BigInteger gb = new BigInteger("3950479633");
    public static final BigInteger gc = new BigInteger("4279487777");
    public static final BigInteger p = new BigInteger("30601606921112410032432608393805465829047821917717470584267666310680326065395764776620804421991229993599582449299178125538470554817507612509750842932546472772927281542367533812667664858137293496323594452523241730766946045871474561289558271889884649967152773403764167811039509089445148956143368326223065134292987");
    public static final BigInteger hb = new BigInteger("8064937149397826415200901961432348477971480358552904700238427259997752274865700044471449711728456089426149113518777956369974054001544381448151926746610197");
    public static final BigInteger hc = new BigInteger("11399706344436040602417332509945893160173442624618567121115927687515199344997854123006118749525719585189972762800725849101443580534927773930703709268195137");

    public static BigInteger f(BigInteger x, BigInteger v) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(x.toByteArray());
            return new BigInteger(encodedHash).mod(v);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigInteger f1(BigInteger x) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] encodedHash = digest.digest(x.toByteArray());
            return new BigInteger(encodedHash).mod(N);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        v = new HashMap<>();
        vShift = new HashMap<>();
        v.put(1, new BigInteger("275852796532165932374346917470713674771"));
        vShift.put(1, v.get(1).modInverse(fiN));
        v.put(2, new BigInteger("202443319805629628462362127606948377951"));
        vShift.put(2, v.get(2).modInverse(fiN));
        v.put(5,new BigInteger("334835037932772510472354128600280403551"));
        vShift.put(5, v.get(5).modInverse(fiN));
        v.put(10,new BigInteger("316550094004925477083819333661325480493"));
        vShift.put(10, v.get(10).modInverse(fiN));
        v.put(50,new BigInteger("257426855238081569338870679159043485279"));
        vShift.put(50, v.get(50).modInverse(fiN));
        v.put(100,new BigInteger("185162837950411616686788266935518918869"));
        vShift.put(100, v.get(100).modInverse(fiN));
        v.put(500,new BigInteger("221380270698521972017894586039332698413"));
        vShift.put(500, v.get(500).modInverse(fiN));
        v.put(1000,new BigInteger("246990797861289466450772551633221338431"));
        vShift.put(1000, v.get(1000).modInverse(fiN));
        v.put(5000,new BigInteger("240196205854433175012464619085335160261"));
        vShift.put(5000, v.get(5000).modInverse(fiN));

        BigInteger a1, b1, c1, o, t, fi, y, alfa, beta, k1, ea, eb, ec, a;
        BigInteger vv = v.get(5000);

        a1 = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(a1);
        b1 = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(b1);
        c1 = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(c1);

        o = BigInteger.probablePrime(1024, new Random()).mod(vv);
        System.out.println(o);
        t = BigInteger.probablePrime(1024, new Random()).mod(vv);
        System.out.println(t);
        fi = BigInteger.probablePrime(1024, new Random()).mod(vv);
        System.out.println(fi);

        y = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(y);
        alfa = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(alfa);
        beta = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(beta);
        System.out.println("---------------------------------------------------------123");

        BigInteger arg1 = y.modPow(vv, N).multiply(c1).multiply(gc.modPow(o, N));
        System.out.println(arg1);
        BigInteger arg2 = alfa.modPow(vv, N).multiply(a1).multiply(ga.modPow(t, N));
        System.out.println(arg2);
        BigInteger arg3 = beta.modPow(vv, N).multiply(b1).multiply(gb.modPow(fi, N));
        System.out.println(arg3);
        System.out.println("---------------------------------------------------------5");

        BigInteger a2, b2, c2, arg11, arg22, arg33;

        a2 = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(a2);
        b2 = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(b2);
        c2 = BigInteger.probablePrime(1024, new Random()).mod(N);
        System.out.println(c2);
        System.out.println("---------------------------------------------------------6");

        ArrayList<BigInteger> result = new ArrayList<>();
        result.add(hc.modPow(c2, p));
        result.add(a2);
        result.add(hb.modPow(b2, p));
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        System.out.println(result.get(2));
        ArrayList<BigInteger> step1 = result;
        System.out.println("---------------------------------------------------------7");

        k1 = BigInteger.probablePrime(1024, new Random()).mod(vv);
        System.out.println(k1);
        System.out.println("---------------------------------------------------------8");

        ec = f(step1.get(0).modPow(c1, p), vv).subtract(o).mod(vv);
        System.out.println(ec);
        eb = f(step1.get(2).modPow(b1, p), vv).subtract(fi).mod(vv);
        System.out.println(eb);
        a = a1.multiply(step1.get(1)).multiply(f1(ec.add(eb))).modPow(k1, N);
        System.out.println(a);
        ea = k1.modInverse(vv).multiply(f(a, vv)).subtract(t).mod(vv);
        System.out.println(ea);
        System.out.println("---------------------------------------------------------9");

        BigInteger U = new BigInteger("qwerty123".getBytes(StandardCharsets.UTF_8));
        System.out.println(U);
        System.out.println("---------------------------------------------------------10");

        BigInteger A1 = arg2.multiply(a2).multiply(f1(ec.add(eb))).multiply(ga.modPow(ea, N)).mod(N);
        System.out.println(A1);
        BigInteger B1 = arg3.multiply(b2).multiply(gb.modPow(eb, N)).mod(N);
        System.out.println(B1);
        BigInteger C1 = arg1.multiply(c2).multiply(gc.modPow(ec, N)).mod(N);
        System.out.println(C1);
        System.out.println("---------------------------------------------------------11");

        BigInteger k2 = BigInteger.probablePrime(1024, new Random()).mod(vv);
        System.out.println(k2);
        System.out.println("---------------------------------------------------------12");

        ArrayList<BigInteger> result1 = new ArrayList<>();
        result1.add(b2);
        result1.add(c2);
        result1.add(k2);
        result1.add(C1.modPow(k2, N).multiply(A1).modPow(vv.modInverse(N), N).mod(N));
        result1.add(C1.modPow(U, N).multiply(B1).modPow(vv.modInverse(N), N).mod(N));
        System.out.println(result1.get(0));
        System.out.println(result1.get(1));
        System.out.println(result1.get(2));
        System.out.println(result1.get(3));
        System.out.println(result1.get(4));
        System.out.println("---------------------------------------------------------13");

        ArrayList<BigInteger> step2 = result1;
        BigInteger cc = c1.multiply(step2.get(1)).mod(N);
        System.out.println(cc);
        BigInteger b = b1.multiply(step2.get(0)).mod(N);
        System.out.println(b);
        BigInteger k = k1.multiply(step2.get(2)).mod(vv);
        System.out.println(k);
        BigInteger C = cc.multiply(gc.modPow(f(step1.get(0).modPow(c1, p), vv), N)).mod(N);
        System.out.println(C);
        BigInteger B = b.multiply(gb.modPow(f(step1.get(2).modPow(b1, p), vv), N)).mod(N);
        System.out.println(B);
        BigInteger A = a.multiply(ga.modPow(f(a, vv), N)).mod(N);
        System.out.println(A);
        BigInteger Sa = step2.get(3).multiply(y.modPow(step2.get(2), N).multiply(alfa).modInverse(N)).
                modPow(k1, N).mod(N);
        System.out.println(Sa);
        BigInteger Sb = step2.get(4).multiply(y.modPow(U, N).multiply(beta).modInverse(N)).mod(N);
        System.out.println(Sb);
        System.out.println("---------------------------------------------------------14");
        System.out.println(Sa.modPow(vv, N));
        System.out.println(C.modPow(k, N).multiply(A).mod(N));
        System.out.println("---------------------------------------------------------");
        System.out.println(Sb.modPow(vv, N));
        System.out.println(C.modPow(U, N).multiply(B).mod(N));

    }

}