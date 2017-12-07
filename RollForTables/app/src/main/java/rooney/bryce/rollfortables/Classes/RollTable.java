package rooney.bryce.rollfortables.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/**
 * Created by Bryce Rooney on 11/9/2017.
 */

public class RollTable {

    public int id;

    public String title;

    public String description;

    public int[] die = new int[2];

    public int numResults;

    public ArrayList<String> resultsList = new ArrayList<String>();

    public ArrayList<Integer> rangesForResults = new ArrayList<Integer>();

    public int source;

    public ArrayList<String> tags = new ArrayList<String>();

    /**
        Blank Constructor
     */
    public RollTable(){

    }
    /**
     * Constructor
     * @param id
     * @param title
     * @param description
     * @param die
     * @param numResults
     */


    public RollTable(int id, String title, String description, int[] die, int numResults){
        this.id = id;
        this.title = title;
        this.description = description;
        this.die = die;
        this.numResults = numResults;
    }

    /**
     *  Getters
     */
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int[] getDie() {
        return die;
    }

    public int getNumResults() {
        return numResults;
    }

    public List<String> getResultsList() {
        return resultsList;
    }

    public List<Integer> getRangesForResults() {
        return rangesForResults;
    }

    public int getSource() {
        return source;
    }

    public List<String> getTags() {
        return tags;
    }

    /**
     * Setters
     *
     */

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDie(int[] die) {
        this.die = die;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public void setResultsList(List<String> resultsList) {
        this.resultsList = resultsList;
    }

    public void setRangesForResults(List<Integer> rangesForResults) {
        this.rangesForResults = rangesForResults;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setResultsListFromJSONString(String s){

    }

    public void setRangesForResultsFromJSONString(String s){

    }

    public void setTagsFromJSONString(String s){

    }


    /**
     * Methods
     */


    public void addResultToList(String result){
        this.resultsList.add(result);
    }

    public void clearResultsList(){
        this.resultsList.clear();
    }

    public void clearRangesForResults(){
        this.rangesForResults.clear();
    }
    
    public void addTag(String tag){
        this.tags.add(tag);
    }
    
    public void removeTag(String tag){
        for(int i = 0; i < this.tags.size(); i++){
            if(this.tags.get(i) == tag){
                this.tags.remove(i);
            }
        }
    }

    public void clearTags(){
        this.tags.clear();
    }

    public String rollOnTable(){
        String resultText = "";
        int resultRoll;
        int minRange = this.die[0];
        int maxRange = this.die[1] * this.die[0];

        //get roll result
        Random roll = new Random();
        resultRoll = roll.nextInt((maxRange - minRange + 1) + minRange);

        //find the range element number that roll falls in

        for(int i =0; i<this.resultsList.size(); i++){
            if( (this.rangesForResults.get(i) <= resultRoll) && (resultRoll <= this.rangesForResults.get(i))){
                resultText = this.resultsList.get(i);
            }
        }

        return resultText;
    }

    public String getResultsListJSONString(){
        JSONObject obj = new JSONObject();
        try {
            for(int i = 0; i < resultsList.size(); i++){
                obj.put(i, resultsList.get(i).toString());
            }

        } catch (JSONException e) {
            trace("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj.toString();
    }

    public String getRangesForResultsJSONString(){
        JSONObject jsonObj =

        return "";
    }

    public String getTagsJSONString(){

        return "";
    }


}
