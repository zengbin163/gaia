package commons.test.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * http://www.cnblogs.com/larryzeal/p/8362216.html
 * <p>
 * Created by zengbin on 2018/1/26.
 */
public class CliTest {
    Options options;
    // CommandLine commandLine;
    CommandLineParser commandLineParser = new DefaultParser();

    @Before
    public void init(){

        options = new Options();


        // commandLineParser.parse(options,args);
        // commandLineParser.parse(options,args,false);
    }

    @Test
    public void r1(){

        Option optionD = Option.builder() //FIXME 应该Option.builder("-v")       注意，这不是命令，而是命令的选项
                                 .longOpt("what") //TODO 选项的完整写法 -v --version
                                 .argName("选项名字")
                                 .desc("选项描述")
                                 .required(true) //该选项是否必须
                                 .hasArg(true) //该选项是否需要一个参数，因为有的选项不需要参数
                                 .valueSeparator('=') //选项与参数连接的符号
                                 .numberOfArgs(5) //该选项最多能接收的参数
                                 .optionalArg(true) //该选项的参数是否可选 required
                                 .type(int.class) //嗯？ 该选项的类型？
                                 .build();


        System.out.println("arg name: " + optionD.getArgName());
        System.out.println("args: " + optionD.getArgs());
        System.out.println("opt: " + optionD.getOpt());
        System.out.println("long opt: " + optionD.getLongOpt());
        System.out.println("type: " + optionD.getType());
        System.out.println("value: " + optionD.getValue());
        System.out.println("value list: " + optionD.getValuesList());
        System.out.println("id: " + optionD.getId());
        System.out.println("desc: " + optionD.getDescription());
    }

    @Test
    public void r2(){
        Option optionD = Option.builder("d") //TODO
                                 .argName("名字")
                                 .hasArg(true)
                                 .optionalArg(true)
                                 .numberOfArgs(5)
                                 .valueSeparator(',')
                                 .desc("描述")
                                 .longOpt("version")
                                 .type(int.class)
                                 .build();
    }

    //更简单的方式定义Options对象！ -- 一些符号的预定义需要查阅javadoc
    @Test
    public void simple(){
        //仅限于单字符的选项
        Options options = PatternOptionBuilder.parsePattern("vp:!f/"); //TODO :意味着后面需要字符串args。!意味着非必需。
        options.getOptions().forEach(e -> {
            System.out.println("opt:               \t\t" + e.getOpt());
            System.out.println("hasArg:            \t\t" + e.hasArg());
            System.out.println("hasArgs:           \t\t" + e.hasArgs());
            System.out.println("hasOptionalArg:    \t\t" + e.hasOptionalArg());
            System.out.println("hasValueSeparator: \t\t" + e.hasValueSeparator());

            System.out.println("required:          \t\t" + e.isRequired());
            System.out.println("--------------------------------");
        });
    }

    //输出命令的帮助信息（类似于shell中的 cmd --help的结果）
    @Test
    public void help(){
        HelpFormatter helpFormatter = new HelpFormatter();

        //注意，下面这些setter都可以忽略
        helpFormatter.setSyntaxPrefix(">>>");
        helpFormatter.setArgName("参数的名字");
        helpFormatter.setLeftPadding(4);
        helpFormatter.setDescPadding(4);
        helpFormatter.setOptPrefix("-");
        helpFormatter.setLongOptPrefix("--");
        helpFormatter.setNewLine("\r\n-------------------\r\n-------------------\r\n");
        helpFormatter.setLongOptSeparator("==");
        helpFormatter.setWidth(18);

        helpFormatter.printHelp("python", PatternOptionBuilder.parsePattern("vp:!f/"));
    }

    @Test
    public void parse() throws ParseException{
        Options options = PatternOptionBuilder.parsePattern("vp:!f/");
        String[] args = {"-v", "-p whereru", "-f http://www.baidu.com"};//TODO 卧槽，这里的args

        CommandLine commandLine = commandLineParser.parse(options, args);

        System.out.println(commandLine.getOptionValue("v"));
        System.out.println(commandLine.getOptionValue("p"));
        System.out.println(commandLine.getOptionValue("f"));

    }

    //TODO 这才是最终的使用
    @Test
    public void parse2() throws ParseException{
        String[] args = {"-v", "-p", "wozeinima", "-f", "http://www.baidu.com"};//TODO 卧槽，这里的args

        CommandLine commandLine = commandLineParser.parse(PatternOptionBuilder.parsePattern("vp:!f/"), args);

        System.out.println(commandLine.getOptionValue("v"));
        System.out.println(commandLine.getOptionValue("p"));
        System.out.println(commandLine.getOptionValue("f"));

    }
}
