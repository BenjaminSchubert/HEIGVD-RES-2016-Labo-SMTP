package ch.heigvd.res.smtp.groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class SpamGroupGenerator {

    public static SpamGroup[] generate(String file, int number) {
        ArrayList<String> usernames = new ArrayList<>();
        SpamGroup[] spamgroups = new SpamGroup[number];

        try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String username;
            while((username = in.readLine()) != null) {
                usernames.add(username);
            }
        } catch (FileNotFoundException e) {
            System.err.println(String.format("The file %s does not exist", file));
            System.exit(3);
        } catch (IOException e) {
            System.err.println(String.format("Error reading file %s", file));
            System.exit(5);
        }

        if(usernames.size() / 3 < number) {
            throw new RuntimeException("Too much groups. Can't put 3 people in each");
        }

        Collections.shuffle(usernames);
        for(int i =0; i < spamgroups.length; i++) {
            spamgroups[i] = new SpamGroup();
        }
        for(int i = 0; i < usernames.size(); i++) {
            spamgroups[i % number].addUser(usernames.get(i));
        }

        return spamgroups;
    }
}
