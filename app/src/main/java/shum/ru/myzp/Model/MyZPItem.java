package shum.ru.myzp.Model;
public class MyZPItem {


    public final String DB_KEY_ID = "ID";
    public final String DB_KEY_FOR_MOUNTH = "for_mounth";
    public final String DB_KEY_TYPE = "type";
    public final String DB_KEY_DATE = "date";
    public final String DB_KEY_VALUE = "value";
    public final String DB_KEY_STS_DATE = "sts_date";
    public final String DB_KEY_STS_VALUE = "sts_VALUE";

    public int intId;
    public String forMounth;
    public String type;
    public String date;
    public String value;
    public String stsDate;
    public String stsValue;







    public MyZPItem(int id, String forMounth, String type, String date, String value, String stsDate, String stsValue  ){
            this.intId = id;
            this.forMounth = forMounth;
            this.type = type;
            this.date = date;
            this.value = value;
            this.stsDate = stsDate;
            this.stsValue = stsValue;

    }


    public String getType(){
        return this.type;
    }

    public String getMonth(){
        return this.forMounth;
    }

    public String getValue(){
        return this.value;
    }

}

