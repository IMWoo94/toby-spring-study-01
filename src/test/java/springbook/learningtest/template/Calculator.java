package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException, FileNotFoundException {
//        BufferedReaderCallback sumCallback = new BufferedReaderCallback(){
//            @Override
//            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
//                Integer sum = 0;
//                String line = null;
//                while((line = br.readLine()) != null){
//                    sum += Integer.valueOf(line);
//                }
//
//                return sum;
//            }
//        };
//
//        return fileReadTemplate(filepath, sumCallback);

        LineCallback sumCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer val) {
                return val + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException, FileNotFoundException {
//        BufferedReaderCallback multiplyCallback = new BufferedReaderCallback() {
//            @Override
//            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
//                Integer sum = 1;
//                String line = null;
//                while((line = br.readLine()) != null){
//                    sum *= Integer.valueOf(line);
//                }
//
//                return sum;
//            }
//        };
//        return fileReadTemplate(filepath, multiplyCallback);
        LineCallback multiplyCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer val) {
                return val * Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException{
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));

            int ret = callback.doSomethingWithReader(br);

            return ret;
        }catch(IOException e){
            System.out.println(e.getMessage());
            throw e;
        }finally {
            if (br != null) {
                try{
                    br.close();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException{
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filepath));
            Integer res = initVal;
            String line = null;
            while((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        }catch(IOException e){
            System.out.println(e.getMessage());
            throw e;
        }finally {
            if (br != null) {
                try{
                    br.close();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }

            }
        }
    }

}
