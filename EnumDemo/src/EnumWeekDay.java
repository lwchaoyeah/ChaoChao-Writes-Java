
/**
 * @description: Java 枚举是一个特殊的类，一般表示一组常量
 * @author Yoghourt
 * @date 2021/1/27 22:43
 * @version 1.0
 */
// 默认修饰符是public，也只能是public!!!
public enum EnumWeekDay {
    // 枚举实例
    // 默认修饰符：public final static
    Mon("Monday", 1), Tue("Tuesday", 2), Wed("Wednesday", 3), Thu("Thursday", 4), Fri("Friday", 5),
    Sat("Saturday", 6), Sun("Sunday", 7);

    // (枚举)成员变量
    private final String day;
    private  final int index;

    // 默认是private访问修饰符，也只能是private!!!
    EnumWeekDay(String day, int idx) {
        this.day = day;
        this.index = idx;
    }

    public String getDay() {
        return day;
    }

    public int getIndex() {
        return index;
    }
}