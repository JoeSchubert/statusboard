package statusboard;

import java.util.HashMap;
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
    
    public static HashMap<String, Integer> departmentMap = new HashMap<String, Integer>();

    public Constants() {
        departmentMap.put(COMMANDING_OFFICER, 0);
        departmentMap.put(EXECUTIVE_OFFICER, 1);
        departmentMap.put(OFFICERS,2);
        departmentMap.put(CHIEFS, 3);
        departmentMap.put(ENGINEERING, 4);
        departmentMap.put(OPERATIONS, 5);
        departmentMap.put(DECK, 6);
        departmentMap.put(SUPPORT, 7);
    }
    
    public int getPos(String department) {
        return departmentMap.get(department);
    }
    
    public String getDepartmentByPos(int position) {
        Set<Map.Entry<String, Integer>> entries = departmentMap.entrySet();
        for( Map.Entry entry : entries) {
            if (entry.getValue().equals(position)) {
                return  (String) entry.getKey();
            }
        }
        return null;
    }
    
    public boolean isValidDept(String department) {
        return departmentMap.containsKey(department);
    }
    
    public String[] getPayGrades() {
        String[] pg = {"", "O6" , "O5" , "O4", "O3", "O2", "O1", "W4", "W3", "W2", "E8", "E7", "E6", "E5", "E4", "E3", "E2", "E1"};
        return pg;
    }
    
    public String[] getRanks(String payGrade) {
        String [] str = new String[]{};
        switch (payGrade) {
            default: break;
            case "O6" : {
                str = new String[] {"CAPT"};
                break;
            }
            case "O5" : {
                str = new String[] {"CDR"};
                break;
             }
            case "O4" : {
                str = new String[] {"LCDR"};
                break;
            }
            case "O3" : {
                str = new String[] {"LT"};
                break;
            }
            case "O2" : {
                str = new String[] {"LTJG"};
                break;
            }
            case "O1" : {
                str = new String[] {"ENS"};
                break;
            }
            case "W4" : {
                str = new String[]{"F&S4", "ENG4"};
                break;
            }
            case "W3" : {
                str = new String[]{"F&S3", "ENG3"};
                break;
            }
            case "W2" : {
                str = new String[]{"F&S2", "ENG2"};
                break;
            }
            case "E8" : {
                str = new String[]{"BMCS", "CSCS" ,"EMCS", "ETCS", "GMCS", "MKCS", "OSCS"};
                break;
            }
            case "E7" : {
                str = new String[]{"BMC", "CSC", "DCC", "EMC", "ETC", "GMC", "ITC", "MEC", "MKC", "OSC", "SKC", "YNC"};
                break;
            }
            case "E6" : {
                str = new String[]{"BM1", "CS1", "DC1", "EM1", "ET1", "IT1", "IS1", "ME1", "MK1", "OS1", "SK1", "YN1"};
                break;
            }
            case "E5" : {
                str = new String[]{"BM2", "CS2", "DC2", "EM2", "ET2", "IT2", "IS2", "ME2", "MK2", "OS2", "SK2", "YN2"};
                break;
            }
            case "E4" : {
                str = new String[]{"BM3", "CS3", "DC3", "EM3",  "ET3", "IT3", "IS3", "ME3", "MK3", "OS3", "SK3", "YN3"};
                break;
            }
            case "E3" : {
                str = new String[]{"SN", "FN", "SNBM", "SNCS", "FNDC", "FNEM", "SNET", "SNIT", "SNIS", "SNME", "FNMK", "SNOS", "SNSK", "SNYN"};
                break;
            }
            case "E2" : {
                str = new String[]{"SA", "FA"};
                break;
            }
        }
        return str;
    }
    
    public String[] getDepartments() {
        return new String[]{"", COMMANDING_OFFICER, EXECUTIVE_OFFICER, OFFICERS, CHIEFS, ENGINEERING, OPERATIONS, DECK, SUPPORT};
    }
    
    public String getDatabaseName() {
        return "jdbc:sqlite:StatusBoard.db";
    }
}
