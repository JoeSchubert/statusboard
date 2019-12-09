package statusboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Constants {

    public final String COMMANDING_OFFICER = "CommandingOfficer";
    public final String EXECUTIVE_OFFICER = "ExecutiveOfficer";
    public final String OFFICERS = "Officers";
    public final String CHIEFS = "Chiefs";
    public final String ENGINEERING = "Engineering";
    public final String OPERATIONS = "Operations";
    public final String DECK = "Deck";
    public final String SUPPORT = "Support";
    public final String TDY = "TDY";
    private final String[] rates = {"AET", "AMT", "AST", "BM", "CS", "DC", "DV",  "EM", "ET", "GM", "HS", "IT", "IS", "ME", "MK", "MST", "MU", "PA", "OS", "SK", "YN"};
    private final String[] specialties = {"AVI", "BNDM", "BOSN", "COMM", "ELC", "F&S", "INF", "MAT", "MED", "PERS", "PSS", "PYA", "WEPS"};

    public static HashMap<String, Integer> departmentMap = new HashMap<String, Integer>();

    public Constants() {
        departmentMap.put(COMMANDING_OFFICER, 0);
        departmentMap.put(EXECUTIVE_OFFICER, 1);
        departmentMap.put(OFFICERS, 2);
        departmentMap.put(CHIEFS, 3);
        departmentMap.put(ENGINEERING, 4);
        departmentMap.put(OPERATIONS, 5);
        departmentMap.put(DECK, 6);
        departmentMap.put(SUPPORT, 7);
        departmentMap.put(TDY, 8);
    }

    public int getPos(String department) {
        return departmentMap.get(department);
    }

    public String getDepartmentByPos(int position) {
        Set<Map.Entry<String, Integer>> entries = departmentMap.entrySet();
        for (Map.Entry entry : entries) {
            if (entry.getValue().equals(position)) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public boolean isValidDept(String department) {
        return departmentMap.containsKey(department);
    }

    public String[] getPayGrades() {
        String[] pg = {"", "O6", "O5", "O4", "O3", "O2", "O1", "CADET", "W4", "W3", "W2", "E9", "E8", "E7", "E6", "E5", "E4", "E3", "E2"};
        return pg;
    }

    public String[]  getRanks(String payGrade) {
        ArrayList <String>  str = new ArrayList<>();
        switch (payGrade) {
            default:
                break;
            case "O6": {
                str.add("CAPT");
                break;
            }
            case "O5": {
                str.add("CDR");
                break;
            }
            case "O4": {
                str.add("LCDR");
                break;
            }
            case "O3": {
                str.add("LT");
                break;
            }
            case "O2": {
                str.add("LTJG");
                break;
            }
            case "O1": {
                str.add("ENS");
                break;
            }
            case "CADET": {
                str.add("CDT");
                break;
            }
            case "W4": {
                for (String spec : specialties) {
                    str.add(spec + "4");
                }
                break;
            }
            case "W3": {
                for (String spec : specialties) {
                    str.add(spec + "3");
                }
                break;
            }
            case "W2": {
                for (String spec : specialties) {
                    str.add(spec + "2");
                }
                break;
            }
            case "E9" : {
                for (String rate : rates)  {
                    str.add(rate + "CM");
                }
                break;
            }
            case "E8": {
                for (String rate : rates)  {
                    str.add(rate + "CS");
                }
                break;
            }
            case "E7": {
                for (String rate : rates) {
                    str.add(rate + "C");
                }
                break;
            }
            case "E6": {
                for (String rate : rates) {
                    str.add(rate + "1");
                }
                break;
            }
            case "E5": {
                for (String rate : rates) {
                    str.add(rate + "2");
                }
                break;
            }
            case "E4": {
                for (String rate : rates) {
                    str.add(rate + "3");
                }
                break;
            }
            case "E3": {
                str.add("SN");
                str.add("FN");
                for (String rate : rates) {
                    str.add(getPrefix(rate, "E3") + rate);
                }
                break;
            }
            case "E2": {
                str.add("SA");
                str.add("FA");
                for (String rate : rates) {
                    str.add(getPrefix(rate, "E2") + rate);
                }
                break;
            }
        }
        String[] retString = new String[str.size()];
        retString = str.toArray(retString);
        return retString;
    }

    public String[] getDepartments() {
        return new String[]{"", COMMANDING_OFFICER, EXECUTIVE_OFFICER, OFFICERS, CHIEFS, ENGINEERING, OPERATIONS, DECK, SUPPORT, TDY};
    }

    public String getDatabaseName() {
        return "jdbc:sqlite:StatusBoard.db";
    }
    
    private String getPrefix(String rating, String paygrade) {
        List<String> deck  = Arrays.asList("AET","AST", "BM", "CS", "DV", "ET", "HS", "IT", "IS", "ME", "MST",  "OS",  "PA",  "SK", "YN");
        if (deck.contains(rating)) {
            if (paygrade.equals("E3")) {
                return "SN";
            }  else if (paygrade.equals("E2")) {
                return "SA";
            }
        } else {
            if (paygrade.equals("E3")) {
                return "FN";
            } else if (paygrade.equals("E2")) {
                return "FA";
            }   
        }
        return "";
    }
}
