package metro.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleProcessor {
    
    private final Pattern CMD_PTRN;
    private final Pattern ARGS_PTRN;
    
    private String cmd;
    private String[] args;
    
    public ConsoleProcessor() {
        final String CMD_REGEX = "(?<cmd>(?<=/)\\S+)(?:[ \\t])*(?<args>.+)?";
        final String ARGS_REGEX = "(?<arg>\".+?\"|\\S+)";
        CMD_PTRN = Pattern.compile(CMD_REGEX);
        ARGS_PTRN = Pattern.compile(ARGS_REGEX);
    }
    
    public boolean processLine(String line) {
        Matcher cmdMatcher = CMD_PTRN.matcher(line);
        if (!cmdMatcher.find()) {
            return false;
        }
        cmd = cmdMatcher.group("cmd");
        String allArgs = cmdMatcher.group("args");
        
        args = null;
        if (allArgs != null) {
            Matcher argsMatcher = ARGS_PTRN.matcher(allArgs);
            int argsCount = (int) argsMatcher.results().count();
            argsMatcher.reset();
            args = new String[argsCount];
            
            for (int i = 0; i < argsCount; ++i) {
                argsMatcher.find();
                args[i] = argsMatcher.group("arg").replace("\"", "");
            }
        }
        
        return true;
    }
    
    public String getCmd() {
        return cmd;
    }
    
    public String[] getArgs() {
        return args;
    }
}
