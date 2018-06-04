package invisibleuniversity.Stories;

public class Helper {
    public static String checkIfFolderIsNotEmpty(String folderName){
        switch (folderName){
            case "university":
                return "25d902c24283ab8cfbac54dfa101ad31-a8e7088217419df289d3a370acaba2de7e1b1167";
            case "src":
                return "fad58de7366495db4650cfefac2fcd61-7d62de2d5dcbbc984272c8baa8f9b673715382b4";
            case "main":
                return "041e2fe3499837ab19cc21ab62bc8e54-42f2aa1c1646c6321f972fd9e1fe8fca576b0fb1";
            case "invisibleUniversity":
                return "ad5f82e879a9c5d6b5b442eb37e50551-1f355066566a4502b9906a6908fc36fef23b50b5";
            case "domain":
                return "a487e2c2715d64b3e27922eea76fd5b8-e7f32332a237d9df97924aa7ef1fbe291da6b01e";
            default:
                return "";
        }
    }
}
