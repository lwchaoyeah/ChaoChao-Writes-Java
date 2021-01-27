
/**
 * @description: 枚举类型测试Demo
 * @author Yoghourt
 * @date 2021/1/27 23:02
 * @version 1.0
 */
public class WeekDayDemo {
    public static void main(String[] args) {
        // 枚举实例序号(orinal())默认是从0开始
        for(int i = 0; i < EnumWeekDay.values().length; i++){
            System.out.println("index: " + EnumWeekDay.values()[i].ordinal() + ", value: " + EnumWeekDay.values()[i]);
        }

        // valueOf()将String转换成枚举实例
        System.out.println("use of ordinal(): "+EnumWeekDay.valueOf("Tue").ordinal());

        // 测试获取枚举实例的成员变量
        for(int i = 0; i < EnumWeekDay.values().length; i++){
            System.out.println("index: " + EnumWeekDay.values()[i].getIndex() + ", value: " + EnumWeekDay.values()[i].getDay());
        }
    }
}
