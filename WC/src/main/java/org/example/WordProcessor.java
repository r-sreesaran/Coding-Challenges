
package org.example;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;

public class WordProcessor {


    /**
     * Design document for the current challenge
     * -l returns the number of lines in the file
     * -w returns the number of words in the file
     * -c returns the number of bytes in the file
     * -m returns the number of characters in the file
     * if no option is specified return the values of -c -l -w
     */
    WordProcessorBean wordprocessorbean = new WordProcessorBean();
    int no_of_lines = 0;
    int total_no_of_words = 0;
    int total_no_of_characters = 0;


    public static void main(String args[]) throws  IOException {
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        String input = scanner.next();
        WordProcessor processor = new WordProcessor();
        processor.operation(input);

    }

    public WordProcessorBean getWordprocessorbean() {
        return wordprocessorbean;
    }

    public String[] processCommand(String command) {

        String[] arguments = command.split("\\s");
        if(Arrays.stream(arguments).anyMatch(s -> s.contains("|"))) {

            String fileName  = arguments[1];
            String option = arguments[4];

            arguments = new String[10] ;
            arguments[0] ="cwcc";
            arguments[1] = option;
            arguments[2] = fileName;

        }
        return arguments;
    }

    public File readFile(String fileName) {
        //File fileObj = new File(file);
        //Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());

        ClassLoader classLoader = getClass().getClassLoader();

        //System.out.println(file.getAbsolutePath());
        return new File(classLoader.getResource(fileName).getFile());

    }



    public long getTotalSpace(File file) {
        return file.length();
    }

    /**
     * This will return the total no of lines, total no of words and total no of characters  in a file s
     * @param file input file
     * @throws IOException when the input file is missing
     */
    public void getNumberofWordsAndLines(File file) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(file.getName());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                total_no_of_characters += line.length()+1;
                if (line.split("\\s|\t").length >= 1 && !line.isBlank()) {
                    //Arrays.stream(line.split(" \\s|\\t")).forEach(System.out::println);
                    String[] words = line.split("\\s|\t");
                    for (String word : words) {
                        if (!word.isBlank() && !word.isEmpty()) {
                            total_no_of_words++;
                        }
                    }
                }
                no_of_lines++;
            }
        }

    }

    public void operation(String command) throws IOException {
        String[] arguments = processCommand(command);
        String option;
        String fileName;
        if(arguments.length==2) {
             fileName = arguments[1];
             option =  "default";
        }
        else {
             option = arguments[1];
             fileName = arguments[2];
        }

        getNumberofWordsAndLines(readFile(fileName));
        switch (option) {
            case "-l":
                wordprocessorbean.setNo_of_lines(no_of_lines);
                System.out.print(no_of_lines+"\t");
                System.out.print(fileName+"\n");
                break;
            case "-c":
                wordprocessorbean.setFile_size(getTotalSpace(readFile(fileName)));
                System.out.print(getTotalSpace(readFile(fileName))+"\t");
                System.out.print(fileName+"\n");
                break;
            case "-w":
                wordprocessorbean.setTotal_no_of_words(total_no_of_words);
                System.out.print(total_no_of_words+"\t");
                System.out.print(fileName+"\n");
                break;
            case "-m":
                wordprocessorbean.setTotal_no_of_characters(total_no_of_characters);
                System.out.print(total_no_of_characters+"\t");
                System.out.print(fileName+"\n");
                break;

            default:
                wordprocessorbean.setNo_of_lines(no_of_lines);
                wordprocessorbean.setTotal_no_of_words(total_no_of_words);
                wordprocessorbean.setFile_size(getTotalSpace(readFile(fileName)));

                System.out.print(no_of_lines+"\t");
                System.out.print(total_no_of_words+"\t");
                System.out.print(getTotalSpace(readFile(fileName))+"\t");
                System.out.print(fileName+"\n");

        }

    }

}
