public class test {
    public static void main(String[] args) {
        String[] parents = {"This", "is", "House"};
        String path = "";
        for (String cur : parents) {
            path.concat(cur + ".");
        }
        System.out.println(path.substring(0, path.lastIndexOf(".")));	
    }
}
