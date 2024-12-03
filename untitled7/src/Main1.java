import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main1 {
    private static int n;
    private static int m;
    private static int[][] a;
    private static int number;

    private static void read() throws IOException {
        number = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] sizes = bufferedReader.readLine().split("\\s");
        n = Integer.parseInt(sizes[0]);
        m = Integer.parseInt(sizes[1]);
        a = new int[n][m];
        for (int i = 0; i < n; i++) {
            a[i] = Arrays.stream(bufferedReader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
        }
    }

    private static class Operation {
        private String result;
        private final String var1;
        private final String op;
        private final String var2;

        public Operation(String result, String var1, String op, String var2) {
            this.result = result;
            this.var1 = var1;
            this.op = op;
            this.var2 = var2;
        }

        @Override
        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(" ");
            stringJoiner.add(result);
            stringJoiner.add("=");
            stringJoiner.add(var1);
            stringJoiner.add(op);
            stringJoiner.add(var2);
            return stringJoiner.toString();
        }
    }

    private static void print(List<Operation> operations) {
        System.out.println(operations.size());
        for (Operation operation: operations) {
            System.out.println(operation);
        }
    }

    private static String varName(int i, int multi) {
        return "v" + i + "_" + (multi < 0 ? "m" : "") + Math.abs(multi);
    }

    private static String v(int i) {
        return "v[" + i + "]";
    }

    private static String r(int i) {
        return "r[" + i + "]";
    }

    private static String addElementaryTerm(List<Operation> operations, Set<String> multiColumn,
                                            Map<String, String> vIsr, int i, int j) {
        if (a[i][j] != 1) {
            String varName = varName(j, a[i][j]);
            if (vIsr.containsKey(varName)) {
                return vIsr.get(varName);
            }
            if (!multiColumn.contains(varName)){
                operations.add(new Operation(varName, v(j), "*", Integer.toString(a[i][j])));
                multiColumn.add(varName);
            }
            return varName;
        } else {
            return v(j);
        }
    }

    private static String sumName() {
        return "s" + number++;
    }

    private static void plus(List<Integer> base, List<Integer> inc, List<Integer> indexes) {
        for (Integer i: indexes) {
            base.set(i, base.get(i) + inc.get(i));
        }
    }

    private static Set<String> checkPromSum(List<List<Integer>> terms, List<String> namesTerms,
                                            List<List<Integer>> meanTerms, List<Integer> sum, List<Integer> meanSum) {
        Set<String> result = new HashSet<>();
        boolean[] flags = new boolean[m];
        for (int i = 0; i < namesTerms.size(); i++) {
            boolean flag = true;
            for (Integer j: meanTerms.get(i)) {
                if (!terms.get(i).get(j).equals(sum.get(j))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result.add(namesTerms.get(i));
                for (Integer j: meanTerms.get(i)) {
                    flags[j] = true;
                }
            }
        }
        for (Integer j: meanSum) {
            if (!flags[j]) {
                return null;
            }
        }
        return result;
    }

    private static void promSum(List<List<Integer>> terms, List<String> namesTerms, List<List<Integer>> meanTerms,
                                Map<String, List<Integer>> sums, Map<String, List<Integer>> meanSums,
                                Map<String, List<Operation>> curOperations,
                                Map<String, Map<String, List<Integer>>> curSums,
                                Map<String, Map<String, List<Integer>>> curMeanSums, Set<String> namesSumsFactor) {
        curOperations.put("cur", new ArrayList<>());
        curSums.put("cur", new HashMap<>());
        curMeanSums.put("cur", new HashMap<>());

        String s = "";
        Set<String> set = null;
        int count = 0;
        int countElementaryTerms = 0;
        for (List<Integer> meanTerm: meanTerms) {
            countElementaryTerms += meanTerm.size();
        }
        for (String sum: sums.keySet()) {
            if (countElementaryTerms > meanSums.get(sum).size()
                    || countElementaryTerms == meanSums.get(sum).size()
                    && terms.get(0).get(meanTerms.get(0).get(0)).equals(sums.get(sum).get(meanTerms.get(0).get(0)))
                    || !namesSumsFactor.contains(sum)) {
                Set<String> result = checkPromSum(terms, namesTerms, meanTerms, sums.get(sum), meanSums.get(sum));
                if (result != null) {
                    if (count < result.size()) {
                        count = result.size();
                        set = result;
                        s = sum;
                        if (count == terms.size()) {
                            break;
                        }
                    }
                }
            }
        }
        if (set != null) {
            for (String term : set) {
                for (int i = 0; i < namesTerms.size(); i++) {
                    if (namesTerms.get(i).equals(term)) {
                        namesTerms.remove(i);
                        meanTerms.remove(i);
                        terms.remove(i);
                        break;
                    }
                }
            }
            namesTerms.add(s);
            meanTerms.add(new ArrayList<>(meanSums.get(s)));
            terms.add(new ArrayList<>(sums.get(s)));
        }

        while (terms.size() > 1) {
            String sumName = sumName();

            curOperations.get("cur").add(new Operation(sumName, namesTerms.get(0), "+", namesTerms.get(1)));
            plus(terms.get(0), terms.get(1), meanTerms.get(1));
            terms.remove(1);
            namesTerms.remove(1);
            namesTerms.set(0, sumName);
            meanTerms.get(0).addAll(meanTerms.get(1));
            meanTerms.remove(1);

            curSums.get("cur").put(sumName, new ArrayList<>(terms.get(0)));
            curMeanSums.get("cur").put(sumName, new ArrayList<>(meanTerms.get(0)));
        }
    }

    private static class Triple {
        private final Set<String> terms;
        private final Boolean op;
        private final Integer factor;

        public Triple(Set<String> terms, Boolean op, Integer factor) {
            this.terms = terms;
            this.op = op;
            this.factor = factor;
        }

        public Set<String> getTerms() {
            return terms;
        }

        public Boolean getOp() {
            return op;
        }

        public Integer getFactor() {
            return factor;
        }
    }

    private static Triple checkFinalSum(List<List<Integer>> terms, List<String> namesTerms,
                                        List<List<Integer>> meanTerms, List<Integer> sum, List<Integer> meanSum) {
        Set<String> result = new HashSet<>();
        boolean[] flags = new boolean[m];
        for (int i = 0; i < namesTerms.size(); i++) {
            boolean flag = true;
            for (Integer j: meanTerms.get(i)) {
                if (!terms.get(i).get(j).equals(sum.get(j))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result.add(namesTerms.get(i));
                for (Integer j: meanTerms.get(i)) {
                    flags[j] = true;
                }
            }
        }
        for (Integer j: meanSum) {
            if (!flags[j]) {
                return null;
            }
        }
        return new Triple(result, null, null);
    }

    private static void finalSum(List<List<Integer>> terms, List<String> namesTerms, List<List<Integer>> meanTerms,
                                 Map<String, List<Integer>> sums, Map<String, List<Integer>> meanSums,
                                 List<Operation> operations, Map<String, List<Operation>> curOperations,
                                 Map<String, Map<String, List<Integer>>> curSums,
                                 Map<String, Map<String, List<Integer>>> curMeanSums, Set<String> namesSumsFactor) {
        if (terms.size() == 1) {
            if (curOperations.get(namesTerms.get(0)) != null) {
                operations.addAll(curOperations.get(namesTerms.get(0)));
                sums.putAll(curSums.get(namesTerms.get(0)));
                meanSums.putAll(curMeanSums.get(namesTerms.get(0)));
            } else {
                String sumName = sumName();
                operations.add(new Operation(sumName, "1", "*", namesTerms.get(0)));
                sums.put(sumName, sums.get(namesTerms.get(0)));
                meanSums.put(sumName, meanSums.get(namesTerms.get(0)));
            }
            return;
        }

        String s = "";
        Triple triple = null;
        int count = 0;
        int countElementaryTerms = 0;
        for (List<Integer> meanTerm: meanTerms) {
            countElementaryTerms += meanTerm.size();
        }
        for (String sum: sums.keySet()) {
            if (countElementaryTerms > meanSums.get(sum).size()
                    || countElementaryTerms == meanSums.get(sum).size()
                    && terms.get(0).get(meanTerms.get(0).get(0)).equals(sums.get(sum).get(meanTerms.get(0).get(0)))
                    || !namesSumsFactor.contains(sum)) {
                Triple result = checkFinalSum(terms, namesTerms, meanTerms, sums.get(sum), meanSums.get(sum));
                if (result != null) {
                    if (count < result.getTerms().size()) {
                        count = result.getTerms().size();
                        triple = result;
                        s = sum;
                        if (count == terms.size()) {
                            break;
                        }
                    }
                }
            }
        }

        if (triple != null) {
            for (String term : triple.getTerms()) {
                for (int i = 0; i < namesTerms.size(); i++) {
                    if (namesTerms.get(i).equals(term)) {
                        namesTerms.remove(i);
                        meanTerms.remove(i);
                        terms.remove(i);
                        break;
                    }
                }
            }
            namesTerms.add(s);
            meanTerms.add(new ArrayList<>(meanSums.get(s)));
            terms.add(new ArrayList<>(sums.get(s)));
        }

        for (String nameTerm: namesTerms) {
            if (curOperations.containsKey(nameTerm)) {
                operations.addAll(curOperations.get(nameTerm));
            }
        }
        for (String nameTerm: namesTerms) {
            if (curSums.containsKey(nameTerm)) {
                sums.putAll(curSums.get(nameTerm));
                meanSums.putAll(curMeanSums.get(nameTerm));
            }
        }

        if (terms.size() == 1) {
            String sumName = sumName();
            operations.add(new Operation(sumName, "1", "*", namesTerms.get(0)));
            sums.put(sumName, sums.get(namesTerms.get(0)));
            meanSums.put(sumName, meanSums.get(namesTerms.get(0)));
            return;
        }

        while (terms.size() > 1) {
            String sumName = sumName();

            operations.add(new Operation(sumName, namesTerms.get(0), "+", namesTerms.get(1)));
            plus(terms.get(0), terms.get(1), meanTerms.get(1));
            terms.remove(1);
            namesTerms.remove(1);
            namesTerms.set(0, sumName);
            meanTerms.get(0).addAll(meanTerms.get(1));
            meanTerms.remove(1);

            sums.put(sumName, new ArrayList<>(terms.get(0)));
            meanSums.put(sumName, new ArrayList<>(meanTerms.get(0)));
        }
    }

    public static void main(String[] args) throws IOException {
        read();
        Set<String> multiColumn = new HashSet<>();
        Map<String, String> vIsr = new HashMap<>();
        List<Operation> operations = new ArrayList<>();
        Map<String, List<Integer>> sums = new HashMap<>();
        Map<String, List<Integer>> meanSums = new HashMap<>();
        Set<String> namesSumsFactor = new HashSet<>();

        for (int i = 0; i < n; i++) {
            int count = 0;
            int indexNotZero = -1;
            for (int j = 0; j < m; j++) {
                if (a[i][j] != 0) {
                    count++;
                    indexNotZero = j;
                }
            }
            if (count == 0) {
                continue;
            }
            if (count == 1) {
                if (a[i][indexNotZero] != 1) {
                    vIsr.put(varName(indexNotZero, a[i][indexNotZero]), r(i));
                }
                operations.add(new Operation(r(i), Integer.toString(a[i][indexNotZero]), "*", v(indexNotZero)));
                continue;
            }
            List<List<Integer>> terms = new ArrayList<>();
            List<String> namesTerms = new ArrayList<>();
            List<List<Integer>> meanTerms = new ArrayList<>();
            Map<String, List<Operation>> curOperations = new HashMap<>();
            Map<String, Map<String, List<Integer>>> curSums = new HashMap<>();
            Map<String, Map<String, List<Integer>>> curMeanSums = new HashMap<>();

            boolean[] flags = new boolean[m];
            for (int j = 0; j < m; j++) {
                if (!flags[j] && a[i][j] != 0 && a[i][j] != 1) {
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(j);
                    for (int k = j + 1; k < m; k++) {
                        if (a[i][j] == a[i][k]) {
                            indexes.add(k);
                        }
                    }
                    if (indexes.size() > 1) {
                        int countBe = 0;
                        for (Integer index : indexes) {
                            if (multiColumn.contains(varName(index, a[i][j]))) {
                                countBe++;
                            }
                        }
                        if (indexes.size() != countBe) {
                            for (Integer index : indexes) {
                                flags[index] = true;
                            }
                            String replacement = "";
                            for (String s: sums.keySet()) {
                                if (meanSums.get(s).size() == indexes.size()) {
                                    List<Integer> sum = sums.get(s);
                                    boolean flg = true;
                                    for (Integer index : indexes) {
                                        if (!sum.get(index).equals(a[i][index])) {
                                            flg = false;
                                            break;
                                        }
                                    }
                                    if (flg) {
                                        replacement = s;
                                        break;
                                    }
                                }
                            }
                            if (!replacement.equals("")) {
                                namesTerms.add(replacement);
                                meanTerms.add(new ArrayList<>(meanSums.get(replacement)));
                                terms.add(new ArrayList<>(sums.get(replacement)));
                                continue;
                            }
                            List<List<Integer>> subterms = new ArrayList<>();
                            List<String> namesSubterms = new ArrayList<>();
                            List<List<Integer>> meanSubTerms = new ArrayList<>();
                            for (Integer index : indexes) {
                                List<Integer> subterm = new ArrayList<>();
                                for (int k = 0; k < m; k++) {
                                    if (k == index) {
                                        subterm.add(1);
                                    } else {
                                        subterm.add(0);
                                    }
                                }
                                subterms.add(subterm);
                                List<Integer> list = new ArrayList<>();
                                list.add(index);
                                meanSubTerms.add(list);
                                namesSubterms.add(v(index));
                            }
                            promSum(subterms, namesSubterms, meanSubTerms, sums, meanSums, curOperations, curSums,
                                    curMeanSums, namesSumsFactor);
                            List<Integer> sum = subterms.get(0);

                            for (int k = 0; k < m; k++) {
                                sum.set(k, sum.get(k) * a[i][j]);
                            }
                            terms.add(sum);
                            String sumName = sumName();
                            namesTerms.add(sumName);
                            meanTerms.add(meanSubTerms.get(0));

                            curSums.get("cur").put(sumName, new ArrayList<>(sum));
                            curMeanSums.get("cur").put(sumName, new ArrayList<>(meanSubTerms.get(0)));
                            curOperations.get("cur").add(new Operation(sumName, Integer.toString(a[i][j]), "*", namesSubterms.get(0)));
                            namesSumsFactor.add(sumName);

                            curSums.put(sumName, curSums.get("cur"));
                            curSums.remove("cur");
                            curMeanSums.put(sumName, curMeanSums.get("cur"));
                            curMeanSums.remove("cur");
                            curOperations.put(sumName, curOperations.get("cur"));
                            curOperations.remove("cur");
                        }
                    }
                }
            }

            for (int j = 0; j < m; j++) {
                if (a[i][j] != 0 && !flags[j]) {
                    List<Integer> term = new ArrayList<>();
                    for (int k = 0; k < m; k++) {
                        if (k == j) {
                            term.add(a[i][j]);
                        } else {
                            term.add(0);
                        }
                    }
                    terms.add(term);
                    List<Integer> list = new ArrayList<>();
                    list.add(j);
                    meanTerms.add(list);
                    namesTerms.add(addElementaryTerm(operations, multiColumn, vIsr, i, j));
                }
            }
            finalSum(terms, namesTerms, meanTerms, sums, meanSums, operations, curOperations, curSums,
                    curMeanSums, namesSumsFactor);
            String sumName = operations.get(operations.size() - 1).result;
            operations.get(operations.size() - 1).result = r(i);
            if (namesSumsFactor.contains(sumName)) {
                namesSumsFactor.add(r(i));
            }
            sums.put(r(i), sums.get(sumName));
            sums.remove(sumName);
            meanSums.put(r(i), meanSums.get(sumName));
            meanSums.remove(sumName);
        }

        print(operations);
    }
}