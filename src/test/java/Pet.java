public class Pet {
    //estamos definindo uma classe para guardar a estrutura de dados sobre os animais(pets)
        public int id;
        public String name;
        public String status;
        public String [] photoUrls;
        
        public class Category {
          public int id;
          public String name;

        }
        public Category category;

        public class Tag {
          public int id;
          public String name;

        }
        public Tag tags[];
}
