import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;



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
                if(strLine[i].equals(token)){
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
                int len = line.length();
                int end = matcher.end();
                results++;
                while(end < len){

                if(matcher.find(end)){
                    end = matcher.end();
                    results ++;
                }
                else{
                    break;
                }}
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
    //this method creates an arraylist of all the words in a file
    public static ArrayList<String> createIndex(String filename) throws FileNotFoundException{
        ArrayList<String> fileWords = new ArrayList<>();
        File file = new File("/Users/kruge/Downloads/sample_text/sample_text/" + filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //split line on whitespace
            String [] strLine = line.split("\\s+");
            //add all words from line to master list
            Collections.addAll(fileWords, strLine);

        }
        scanner.close();
        return fileWords;
    }
    //this method searches a given arraylist for the token
    public static int searchStaticIndex(String token, ArrayList<String> fileWords){
        return Collections.frequency(fileWords, token);

    }


    //this method creates an arraylist of all words in a given file then searches the list for the token
    public static int searchIndex(String target, String filename) throws FileNotFoundException {
        File file = new File("/Users/kruge/Downloads/sample_text/sample_text/" + filename);
        Scanner scanner = new Scanner(file);
        ArrayList<String> fileWords = new ArrayList<>();


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //split line on whitespace
            String [] strLine = line.split("\\s+");
            //add all words from line to master list
            Collections.addAll(fileWords, strLine);

            }
        int results = Collections.frequency(fileWords, target);
        scanner.close();


        return results;
    }
    public static Double getMean(ArrayList<Long> values){
        Double sum = 0.00;
        Double mean = 0.00;
        for(Long x : values){
            sum = sum + x;

        }
        mean = sum / values.size();
        return mean;
    }
    public static Long getMax(ArrayList<Long> values){
        return Collections.max(values);
    }
    public static Long getMin(ArrayList<Long> values){
        return Collections.min(values);
    }

    public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);
        return result;
    }


    public static void main(String[] args) throws IOException {
        //enter p_test and 1, 2 or 3 as arguments to run performance test, else it will run normally
        String [] filenames = {"french_armed_forces.txt", "hitchhikers.txt", "warp_drive.txt"};
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("p_test")){
                System.out.println("Performance test...\n");
                int max = 200000;
                ArrayList<String> randomwords = getRandomWords();
                HashMap<String, ArrayList<Long>> results = new HashMap<>();
                ArrayList<Long> stringsearchtimes = new ArrayList<>();
                ArrayList<Long> regexsearchtimes = new ArrayList<>();
                ArrayList<Long> indexsearchtimes = new ArrayList<>();
                ArrayList<Long> staticindexsearchtimes = new ArrayList<>();

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


                HashMap<String, ArrayList<String>> fileWordsMap = new HashMap<>();
                for(String filename : filenames){
                    ArrayList<String> filewords = createIndex(filename);
                    fileWordsMap.put(filename, filewords);
                }

                for(int i = 0;i<max;i++){
                    System.out.print("\r Running performance test using Static Indexed search, search " + i + " of " + max);
                    String word = randomwords.get(random.nextInt(randomwords.size()));
                    long startTime = System.currentTimeMillis();
                    for(String filename : fileWordsMap.keySet()){


                            int staticresults = searchStaticIndex(word, fileWordsMap.get(filename));








                    }
                    long stopTime = System.currentTimeMillis();
                    long duration = stopTime - startTime;
                    staticindexsearchtimes.add(duration);

                }
                results.put("Static", staticindexsearchtimes);

                FileWriter f0 = new FileWriter("outputstring.txt");
                for(Long f : stringsearchtimes){
                    f0.write(String.valueOf(f) + "\n");

                }
                f0.close();
                FileWriter f1 = new FileWriter("outputregex.txt");
                for(Long g : regexsearchtimes){
                    f1.write(String.valueOf(g) + "\n");

                }
                f1.close();
                FileWriter f2 = new FileWriter("outputindex.txt");
                for(Long h : indexsearchtimes){
                    f2.write(String.valueOf(h) + "\n");
                }
                f2.close();
                FileWriter f3 = new FileWriter("outputstatic.txt");
                for(Long j : staticindexsearchtimes){
                    f3.write(String.valueOf(j) + "\n");

                }
                f3.close();

                System.out.println("\n Performance test finished. Mean durations: \n");
                System.out.println("String match: " + getMean(stringsearchtimes) + " , Min: " + getMin(stringsearchtimes) + " , Max: " + getMax(stringsearchtimes));
                System.out.println("Regex match: " + getMean(regexsearchtimes) + " , Min: " + getMin(regexsearchtimes) + " , Max: " + getMax(regexsearchtimes));
                System.out.println("Index search: " + getMean(indexsearchtimes) + " , Min: " + getMin(indexsearchtimes) + " , Max: " + getMax(indexsearchtimes));
                System.out.println("Static Index search: " + getMean(staticindexsearchtimes) + " , Min: " + getMin(staticindexsearchtimes) + " , Max: " + getMax(staticindexsearchtimes));
                System.exit(0);











            }else{
                System.out.println("Invalid arugment, exiting now");
                System.exit(0);
            }
        }
        HashMap<String, ArrayList<String>> fileWordsMap = new HashMap<>();
        //here is where i pre-process the files for the static index search method
        for(String filename : filenames){
            ArrayList<String> filewords = createIndex(filename);
            fileWordsMap.put(filename, filewords);
        }
        System.out.println("Please enter search term: ");
        Scanner scanner = new Scanner(System.in);
        String searchterm = scanner.nextLine();
        System.out.println("Please select a search method: 1) String Match 2) Regular Expression 3) Indexed at Search Time 4) Indexed Prior to Search");
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
                        int result = searchRegex(searchterm, filename);
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
            case 4:

                for(String filename : fileWordsMap.keySet()){

                    int result = searchStaticIndex(searchterm, fileWordsMap.get(filename));
                    searchResults.put(filename, result);

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
