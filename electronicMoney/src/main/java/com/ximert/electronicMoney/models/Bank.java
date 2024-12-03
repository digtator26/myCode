package com.ximert.electronicMoney.models;

import com.google.gson.Gson;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Bank {

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

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static BigInteger f(BigInteger x, BigInteger v) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(x.toByteArray());
            return new BigInteger(encodedHash).multiply(v).mod(v);
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

    private static Checks checks;
    private static Register register;
    private static final String path = "data\\Bank.txt";
    private static final String pathRegister = "data\\Register.txt";

    private Bank() {}

    public static void initialization() {
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

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            checks = new Gson().fromJson(br, Checks.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(pathRegister))) {
            register = new Gson().fromJson(br, Register.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add(String login, Long check) {
        checks.getChecks().put(login, check);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            new Gson().toJson(checks, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRegister(ElectronicCoin electronicCoin) {
        register.getCoins().add(new SpentElectronicCoin(electronicCoin.getA(), electronicCoin.getC(),
                electronicCoin.getK(), electronicCoin.getV(), electronicCoin.getSa(), BigInteger.ONE, BigInteger.ONE));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathRegister))) {
            new Gson().toJson(register, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRegister(SpentElectronicCoin electronicCoin) {
        register.getCoins().add(new SpentElectronicCoin(electronicCoin.getA(), electronicCoin.getC(),
                electronicCoin.getK(), electronicCoin.getV(), electronicCoin.getSa(),
                electronicCoin.getX(), electronicCoin.getR()));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathRegister))) {
            new Gson().toJson(register, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Long get(String login) {
        return checks.getChecks().get(login);
    }

    private static BigInteger a2, b2, c2, arg11, arg22, arg33, vv;

    public static ArrayList<BigInteger> step1(BigInteger v, BigInteger arg1, BigInteger arg2, BigInteger arg3) {
        a2 = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        b2 = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        c2 = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        vv = v;
        arg11 = arg1;
        arg22 = arg2;
        arg33 = arg3;
        ArrayList<BigInteger> result = new ArrayList<>();
        result.add(hc.modPow(c2, p));
        result.add(a2);
        result.add(hb.modPow(b2, p));
        return result;
    }

    public static ArrayList<BigInteger> step2(BigInteger U,BigInteger ea, BigInteger eb, BigInteger ec) {
        BigInteger A1 = arg22.multiply(a2).multiply(f1(ec.add(eb))).multiply(ga.modPow(ea, N)).mod(N);
        BigInteger B1 = arg33.multiply(b2).multiply(gb.modPow(eb, N)).mod(N);
        BigInteger C1 = arg11.multiply(c2).multiply(gc.modPow(ec, N)).mod(N);
        BigInteger k2 = BigInteger.probablePrime(1024, new Random()).mod(vv);
        ArrayList<BigInteger> result = new ArrayList<>();
        result.add(b2);
        result.add(c2);
        result.add(k2);
        result.add(C1.modPow(k2, N).multiply(A1).modPow(vv.modInverse(N), N).mod(N));
        result.add(C1.modPow(U, N).multiply(B1).modPow(vv.modInverse(N), N).mod(N));
        return result;
    }

}
