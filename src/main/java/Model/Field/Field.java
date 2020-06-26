package Model.Field;


    public class Field implements Cloneable{

        protected String fieldName;

        private String string;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Field(String fieldName, String string) {
            this.fieldName = fieldName;
            this.string = string;
        }

        public Field(String fieldName) {
            this.fieldName = fieldName;
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

