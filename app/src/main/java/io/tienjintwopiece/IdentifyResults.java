package io.tienjintwopiece;

import java.util.ArrayList;

/**
 * Created by Owen on 4/16/16.
 */
public class IdentifyResults {
    public ArrayList<String> tags;
    public ArrayList<String> probabilities;

    public IdentifyResults(ArrayList<String> tags, ArrayList<String> probabilities) {
        this.tags = tags;
        this.probabilities = probabilities;
    }
}
