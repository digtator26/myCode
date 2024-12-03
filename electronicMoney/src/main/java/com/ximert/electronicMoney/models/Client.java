package com.ximert.electronicMoney.models;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Client {

    private String login;
    private String password;
    private String fio;
    private Set<ElectronicCoin> wallet;
    private Set<SpentElectronicCoin> spentWallet;

    public Client(String login, String password, String fio, Set<ElectronicCoin> wallet, Set<SpentElectronicCoin> spentWallet) {
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.wallet = wallet;
        this.spentWallet = spentWallet;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFio() {
        return fio;
    }

    public Set<ElectronicCoin> getWallet() {
        return wallet;
    }

    public Set<SpentElectronicCoin> getSpentWallet() {
        return spentWallet;
    }

    public ArrayList<BigInteger> getPayment1(ElectronicCoin coin) {
        ArrayList<BigInteger> array = new ArrayList<>();
        array.add(coin.getA());
        array.add(coin.getB());
        array.add(coin.getC());
        array.add(coin.getV());
        return array;
    }

    private BigInteger a1, b1, c1, o, t, fi, y, alfa, beta, k1, ea, eb, ec, a;

    public ElectronicCoin payment(int c) {
        BigInteger v = Bank.v.get(c);
        a1 = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        b1 = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        c1 = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);

        o = BigInteger.probablePrime(1024, new Random()).mod(v);
        t = BigInteger.probablePrime(1024, new Random()).mod(v);
        fi = BigInteger.probablePrime(1024, new Random()).mod(v);

        y = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        alfa = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);
        beta = BigInteger.probablePrime(1024, new Random()).mod(Bank.N);

        BigInteger arg1 = y.modPow(v, Bank.N).multiply(c1).multiply(Bank.gc.modPow(o, Bank.N));
        BigInteger arg2 = alfa.modPow(v, Bank.N).multiply(a1).multiply(Bank.ga.modPow(t, Bank.N));
        BigInteger arg3 = beta.modPow(v, Bank.N).multiply(b1).multiply(Bank.gb.modPow(fi, Bank.N));

        ArrayList<BigInteger> step1 = Bank.step1(v, arg1, arg2, arg3);

        k1 = BigInteger.probablePrime(1024, new Random()).mod(v);

        ec = Bank.f(step1.get(0).modPow(c1, Bank.p), v).subtract(o).mod(v);
        eb = Bank.f(step1.get(2).modPow(b1, Bank.p), v).subtract(fi).mod(v);
        a = a1.multiply(step1.get(1)).multiply(Bank.f1(ec.add(eb))).modPow(k1, Bank.N);
        ea = k1.modInverse(v).multiply(Bank.f(a, v)).subtract(t).mod(v);

        BigInteger U = new BigInteger(login.getBytes(StandardCharsets.UTF_8));
        ArrayList<BigInteger> step2 = Bank.step2(U, ea, eb, ec);

        BigInteger cc = c1.multiply(step2.get(1)).mod(Bank.N);
        BigInteger b = b1.multiply(step2.get(0)).mod(Bank.N);
        BigInteger k = k1.multiply(step2.get(2)).mod(v);
        BigInteger C = cc.multiply(Bank.gc.modPow(Bank.f(step1.get(0).modPow(c1, Bank.p), v), Bank.N)).mod(Bank.N);
        BigInteger B = b.multiply(Bank.gb.modPow(Bank.f(step1.get(2).modPow(b1, Bank.p), v), Bank.N)).mod(Bank.N);
        BigInteger A = a.multiply(Bank.ga.modPow(Bank.f(a, v), Bank.N)).mod(Bank.N);
        BigInteger Sa = step2.get(3).multiply(y.modPow(step2.get(2), Bank.N).multiply(alfa).modInverse(Bank.N)).
                modPow(k1, Bank.N).mod(Bank.N);
        BigInteger Sb = step2.get(4).multiply(y.modPow(U, Bank.N).multiply(beta).modInverse(Bank.N)).mod(Bank.N);
        ElectronicCoin electronicCoin = new ElectronicCoin(a, b, cc, k, v, Sa, Sb);
        wallet.add(electronicCoin);
        Clients.add(login, this);
        return electronicCoin;
    }

    public ElectronicCoin delete(int c) {
        ElectronicCoin del = null;
        for (ElectronicCoin coin: wallet) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    if (i == c) {
                        del = coin;
                        break;
                    }
                }
            }
        }
        wallet.remove(del);
        Clients.add(login, this);
        return del;
    }

    public SpentElectronicCoin deleteSpent(int c) {
        SpentElectronicCoin del = null;
        for (SpentElectronicCoin coin: spentWallet) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    if (i == c) {
                        del = coin;
                        break;
                    }
                }
            }
        }
        spentWallet.remove(del);
        Clients.add(login, this);
        return del;
    }

    public void add(ElectronicCoin coin) {
        wallet.add(coin);
        Clients.add(login, this);
    }

    public void addSpent(SpentElectronicCoin coin) {
        spentWallet.add(coin);
        Clients.add(login, this);
    }

}
