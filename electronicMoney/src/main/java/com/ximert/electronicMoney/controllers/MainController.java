package com.ximert.electronicMoney.controllers;

import com.ximert.electronicMoney.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
public class MainController {

    private String login;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("error", "");
        return "home";
    }

    @PostMapping("/home")
    public String postHome(@RequestParam String login, @RequestParam String password, Model model) {
        if (Bank.get(login) == null) {
            model.addAttribute("error", "Такого логина нет");
            return "home";
        }
        if (Clients.getCount(login) == 0) {
            model.addAttribute("error", "У вас закончились попытки на вход");
            return "home";
        }
        String passWord = password;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            passWord = Bank.byteArrayToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!Clients.get(login).getPassword().equals(passWord)) {
            Clients.inc(login);
            model.addAttribute("error", "Пароль неверный. У вас осталось попыток: "
                    + Clients.getCount(login));
            return "home";
        }
        this.login = login;
        Clients.defaultCount(login);
        return "redirect:/wallet";
    }

    @GetMapping("/spentWallet")
    public String spentWallet(Model model) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (SpentElectronicCoin coin: Clients.get(login).getSpentWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    stringJoiner.add(i.toString());
                }
            }
        }
        model.addAttribute("coins", stringJoiner.toString());
        return "spentWallet";
    }

    @GetMapping("/wallet")
    public String wallet(Model model) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (ElectronicCoin coin: Clients.get(login).getWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    stringJoiner.add(i.toString());
                }
            }
        }
        model.addAttribute("coins", stringJoiner.toString());
        return "wallet";
    }

    @PostMapping("/wallet")
    public String walletPost(@RequestParam String coins, Model model) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (ElectronicCoin coin: Clients.get(login).getWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    stringJoiner.add(i.toString());
                }
            }
        }
        model.addAttribute("coins", stringJoiner.toString());
        int c;
        try {
            c = Integer.parseInt(coins);
        } catch (NumberFormatException exception) {
            model.addAttribute("error", "Введите число");
            return "wallet";
        }
        if(c != 1 && c != 2 && c != 5 && c != 10 && c != 50 && c != 100 && c != 500 && c != 1000 && c != 5000) {
            model.addAttribute("error", "Монеты с таким номиналом нет");
            return "wallet";
        }
        boolean flag = false;
        for (ElectronicCoin coin: Clients.get(login).getWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    if (i == c) {
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            Bank.add(login, Bank.get(login) + c);
            Bank.addRegister(Clients.get(login).delete(c));
            stringJoiner = new StringJoiner(", ");
            for (ElectronicCoin coin: Clients.get(login).getWallet()) {
                for (Integer i: Bank.v.keySet()) {
                    if (Bank.v.get(i).equals(coin.getV())) {
                        stringJoiner.add(i.toString());
                    }
                }
            }
            model.addAttribute("coins", stringJoiner.toString());
        } else {
            model.addAttribute("error", "Монеты с таким номиналом у вас нет");
        }
        return "wallet";
    }

    @PostMapping("/spentWallet")
    public String spentWalletPost(@RequestParam String coins, Model model) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (SpentElectronicCoin coin: Clients.get(login).getSpentWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    stringJoiner.add(i.toString());
                }
            }
        }
        model.addAttribute("coins", stringJoiner.toString());
        int c;
        try {
            c = Integer.parseInt(coins);
        } catch (NumberFormatException exception) {
            model.addAttribute("error", "Введите число");
            return "spentWallet";
        }
        if(c != 1 && c != 2 && c != 5 && c != 10 && c != 50 && c != 100 && c != 500 && c != 1000 && c != 5000) {
            model.addAttribute("error", "Монеты с таким номиналом нет");
            return "spentWallet";
        }
        boolean flag = false;
        for (SpentElectronicCoin coin: Clients.get(login).getSpentWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coin.getV())) {
                    if (i == c) {
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            Bank.add(login, Bank.get(login) + c);
            Bank.addRegister(Clients.get(login).deleteSpent(c));
            stringJoiner = new StringJoiner(", ");
            for (ElectronicCoin coin: Clients.get(login).getWallet()) {
                for (Integer i: Bank.v.keySet()) {
                    if (Bank.v.get(i).equals(coin.getV())) {
                        stringJoiner.add(i.toString());
                    }
                }
            }
            model.addAttribute("coins", stringJoiner.toString());
        } else {
            model.addAttribute("error", "Монеты с таким номиналом у вас нет");
        }
        return "spentWallet";
    }

    @GetMapping("/bank")
    public String bank(Model model) {
        model.addAttribute("check", Bank.get(login));
        return "bank";
    }

    @PostMapping("/bank")
    public String bankPost(@RequestParam String check, Model model) {
        model.addAttribute("check", Bank.get(login));
        int c;
        try {
            c = Integer.parseInt(check);
        } catch (NumberFormatException exception) {
            model.addAttribute("error", "Введите число");
            return "bank";
        }
        if(c != 1 && c != 2 && c != 5 && c != 10 && c != 50 && c != 100 && c != 500 && c != 1000 && c != 5000) {
            model.addAttribute("error", "Монеты с таким номиналом нет");
            return "bank";
        }
        if (Bank.get(login) < c) {
            model.addAttribute("error", "Недостаточно средств");
            return "bank";
        }
        Bank.add(login, Bank.get(login) - c);
        Clients.get(login).payment(c);
        model.addAttribute("check", Bank.get(login));
        return "bank";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentPost(@RequestParam String loginNext, @RequestParam String coin, Model model) {
        if(Bank.get(loginNext) == null) {
            model.addAttribute("error", "Некорректный логин");
            return "payment";
        }
        int c;
        try {
            c = Integer.parseInt(coin);
        } catch (NumberFormatException exception) {
            model.addAttribute("error", "Введите число");
            return "payment";
        }
        if(c != 1 && c != 2 && c != 5 && c != 10 && c != 50 && c != 100 && c != 500 && c != 1000 && c != 5000) {
            model.addAttribute("error", "Монеты с таким номиналом нет");
            return "payment";
        }
        boolean flag = false;
        for (ElectronicCoin coi: Clients.get(login).getWallet()) {
            for (Integer i: Bank.v.keySet()) {
                if (Bank.v.get(i).equals(coi.getV())) {
                    if (i == c) {
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            ElectronicCoin electronicCoin = Clients.get(login).delete(c);
            BigInteger x = BigInteger.probablePrime(1024, new Random());
            Clients.get(loginNext).addSpent(new SpentElectronicCoin(electronicCoin.getA(), electronicCoin.getC(),
                    electronicCoin.getK(), electronicCoin.getV(), electronicCoin.getSa(),
                    x, electronicCoin.getK().multiply(x).add(new BigInteger(login.getBytes(StandardCharsets.UTF_8)))));
        } else {
            model.addAttribute("error", "Монеты с таким номиналом у вас нет");
        }
        return "payment";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@RequestParam String fio, @RequestParam String login,
                                   @RequestParam String password, @RequestParam String password1, Model model) {
        if(fio.length() == 0) {
            model.addAttribute("error", "Поле ФИО незаполнено");
            return "registration";
        }
        if(Bank.get(login) != null || login.length() < 5) {
            model.addAttribute("error", "Некорректный логин");
            return "registration";
        }
        if(password.length() < 8 || !password.equals(password1)) {
            model.addAttribute("error", "Некорректный пароль");
            return "registration";
        }
        String passWord = password;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            passWord = Bank.byteArrayToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Clients.add(login, new Client(login, passWord, fio, new HashSet<>(), new HashSet<>()));
        Bank.add(login, 1000000L);
        return "home";
    }

}
