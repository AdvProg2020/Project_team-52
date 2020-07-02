package Model.Field;


    public class SingleString extends Field {

        private String string;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public SingleString(String fieldName, String string) {
            super(fieldName);
            this.string = string;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return "SingleString{" +
                    "string='" + string + '\'' +
                    ", fieldName='" + fieldName + '\'' +
                    '}';
        }
    }
}
