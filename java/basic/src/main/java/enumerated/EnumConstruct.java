package enumerated;

public enum EnumConstruct {
    A(1, "b"),
    B(2, "c");

    private int key;

    private String value;


    EnumConstruct(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public  static String getValue(int key) {
        EnumConstruct[] array = EnumConstruct.values();
        for (int index = 0; index < array.length; index++) {
            if (array[index].getKey() == key) {
                return array[index].getValue();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(EnumConstruct.getValue(1));
    }
}


