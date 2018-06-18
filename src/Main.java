import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
//TODO write notes on the tree map sorting stuff so i understand it better
//TODO read in random words list for performance test
//TODO write performance test
//TODO add leucine version of index methods

public class Main {

    public static int searchString(String token, String filename) throws FileNotFoundException {
        File file = new File("/Users/kruge/Downloads/sample_text/sample_text/" + filename);
        Scanner scanner = new Scanner(file);
        int lineNum = 0;
        int results = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNum++;
            //split line on whitespace
            String [] strLine = line.split("\\s+");
            //iterate through line's string array
            for(int i = 0;i<strLine.length;i++){
                if(strLine[i].equalsIgnoreCase(token)){
                    results++;

                }

            }
        }
        scanner.close();

        return results;

    }
    public static int searchRegex(String token, String filename) throws FileNotFoundException {
        File file = new File("/Users/kruge/Downloads/sample_text/sample_text/" + filename);
        Scanner scanner = new Scanner(file);
        Pattern pattern = Pattern.compile("\\b" + token + "\\b");
        int lineNum = 0;
        int results = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNum++;
            //split line on whitespace
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                System.out.println("I found a match on line " + lineNum);
                results++;
            }

            }

        scanner.close();
        return results;

    }
    public static ArrayList<String> getRandomWords(){
        File file = new File("/Users/kruge/Downloads/sample_text/sample_text/random_words_list.txt");
        ArrayList<String> randomwords = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                randomwords.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return randomwords;

    }
    public static int searchIndex(String target, String filename) throws FileNotFoundException {
        File file = new File("/Users/kruge/Downloads/sample_text/sample_text/" + filename);
        Scanner scanner = new Scanner(file);
        ArrayList<String> fileWords = new ArrayList<>();

        int results = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //split line on whitespace
            String [] strLine = line.split("\\s+");
            //add all words from line to master list
            Collections.addAll(fileWords, strLine);

            }
        results = Collections.frequency(fileWords, target);
        scanner.close();


        return results;
    }
    public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);
        return result;
    }

   public static Long getMean(ArrayList<Long> values){

        Long sum = Long.valueOf(0);
        for(Long x : values){
            sum = sum + x;

        }
       Long mean = sum / values.size();
       return mean;


   }
    public static void main(String[] args) {
        //enter p_test and 1, 2 or 3 as arguments to run performance test, else it will run normally
        String [] filenames = {"french_armed_forces.txt", "hitchhikers.txt", "warp_drive.txt"};
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("p_test")){
                System.out.println("Performance test...\n");
                int max = 2000000;
                ArrayList<String> randomwords = getRandomWords();
                HashMap<String, ArrayList<Long>> results = new HashMap<>();
                ArrayList<Long> stringsearchtimes = new ArrayList<>();
                ArrayList<Long> regexsearchtimes = new ArrayList<>();
                ArrayList<Long> indexsearchtimes = new ArrayList<>();

                Random random = new Random();
                for(int i = 0;i<max;i++){
                    System.out.print("\r Running performance test using String match, search " + i + " of " + max);
                    String word = randomwords.get(random.nextInt(randomwords.size()));

                    long startTime = System.currentTimeMillis();
                    for(String filename : filenames){
                        try {

                            int stringresults = searchString(word, filename);





                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    long stopTime = System.currentTimeMillis();
                    long duration = stopTime - startTime;
                    stringsearchtimes.add(duration);

                }
                results.put("String", stringsearchtimes);
                for(int i = 0;i<max;i++){
                    System.out.print("\r Running performance test using Regex match, search " + i + " of " + max);
                    String word = randomwords.get(random.nextInt(randomwords.size()));
                    long startTime = System.currentTimeMillis();
                    for(String filename : filenames){
                        try {

                            int regexresults = searchRegex(word, filename);





                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    long stopTime = System.currentTimeMillis();
                    long duration = stopTime - startTime;
                    regexsearchtimes.add(duration);

                }
                results.put("Regex", regexsearchtimes);

                for(int i = 0;i<max;i++){
                    System.out.print("\r Running performance test using Indexed search, search " + i + " of " + max);
                    String word = randomwords.get(random.nextInt(randomwords.size()));
                    long startTime = System.currentTimeMillis();
                    for(String filename : filenames){
                        try {

                            int indexresults = searchIndex(word, filename);





                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    long stopTime = System.currentTimeMillis();
                    long duration = stopTime - startTime;
                    indexsearchtimes.add(duration);

                }
                results.put("Index", indexsearchtimes);

                for(String key : results.keySet()){
                    ArrayList<Long> vals = results.get(key);
                    System.out.println("Search Type: " + key + " , Mean duration: " + getMean(vals));
                }








            }else{
                System.out.println("Invalid arugment, exiting now");
                System.exit(0);
            }
        }
        System.out.println("Please enter search term: ");
        Scanner scanner = new Scanner(System.in);
        String searchterm = scanner.nextLine();
        System.out.println("Please select a search method: 1) String Match 2) Regular Expression 3) Indexed ");
        int searchmethod = scanner.nextInt();
        HashMap<String, Integer> searchResults = new HashMap<>();
        long startTime = System.currentTimeMillis();
        switch(searchmethod){

            case 1:
                for(String filename : filenames){
                    try{
                        int result = searchString(searchterm, filename);
                        searchResults.put(filename, result);
                    }catch(FileNotFoundException ex){
                        System.out.println("File " + filename + " not found");

                    }
                }
                break;
            case 2:
                for(String filename : filenames){
                    try{
                        int result = searchIndex(searchterm, filename);
                        searchResults.put(filename, result);
                    }catch(FileNotFoundException ex){
                        System.out.println("File " + filename + " not found");

                    }
                }
                break;
            case 3:
                for(String filename : filenames){
                    try{
                        int result = searchIndex(searchterm, filename);
                        searchResults.put(filename, result);
                    }catch(FileNotFoundException ex){
                        System.out.println("File " + filename + " not found");

                    }
                }
                break;

        }






        long stopTime = System.currentTimeMillis();
        TreeMap<String, Integer> sortedMap = sortMapByValue(searchResults);
        for(Map.Entry<String, Integer> entry : sortedMap.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue() + " results");
        }
        System.out.println("Duration: " + (stopTime - startTime) + "ms");
    }

}
