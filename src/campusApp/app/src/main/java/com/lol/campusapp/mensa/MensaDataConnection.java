package com.lol.campusapp.mensa;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MensaDataConnection {
    public static final String URL = "https://studentenwerk-marburg.de/essen-trinken/speisekarte/";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static Document loadedDocument = null; //do not direktly read this, instead use getDoc() method
    private static FutureTask<Document> futureTask = null;


    /** Will start a thread that will get an up-to-date document containing the MensaData
     * from the website (url given in MensaDataConnection.URL)
     */
    public static void preloadDoc() {
        Callable<Document> callable = () -> {
            Document result = null;
            try {
                result = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        };

        futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
    }

    /** Will return the document if it has already been loaded, otherwise loads the Doc and then
     * returns it.
     * This Method is to be used instead of directly accessing the field doc of this class
     * @return the document
     */
    private static Document getDoc() {
        if (loadedDocument != null) {
            //already loaded doc
            return loadedDocument;
        }

        if (futureTask == null) {
            preloadDoc();
        }

        //thread started but not yet read result into doc
        try {
            loadedDocument = futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return loadedDocument;
    }

    private MensaDataConnection(){}

    /** liefert die Kennzeichnung der Zusatzstoffe
     */
    public static String getIngredientInfo() {
        Document doc = getDoc();
        if (doc == null) {
            return "";
        }

        Element ingredientList = doc.getElementsByClass("neo-menu-single-additions")
                .first().selectFirst("ul");

        return ingredientList.text();
    }

    /** Will return all Meals that are served in the {@param canteen} in the specified {@param dateRange}
     * from the website (url given in MensaDataConnection.URL)
     * @param canteen specifies the canteen to get the meals for
     * @param dateRange specifies the daterange to get the meals for
     * @return All Meals that fit the requirements
     */
    public static List<Meal> getMeals(Canteen canteen, MensaDateRange dateRange) {
        Document doc = getDoc();
        if (doc == null) {
            return new LinkedList<>();
        }

        Elements dishesDiv = doc.getElementsByClass("neo-menu-single-dishes");

        Set<LocalDate> dates = getDates(dateRange, doc);

        Element table = dishesDiv.select("table").first();

        Elements rows = table.getElementsByAttributeValue("data-canteen", canteen.getAttributeCode());

        List<Meal> meals = new ArrayList<>();

        for (Element row : rows) {
            String dateString = row.attr("data-date");
            LocalDate date = LocalDate.parse(dateString);
            if (! dates.contains(date)) {
                //meal not in requested daterange
                continue;
            }
            String title = row.getElementsByClass("neo-menu-single-title").text();
            String type = row.getElementsByClass("neo-menu-single-type").text();
            String priceString = row.getElementsByClass("neo-menu-single-price").text();
            priceString = priceString   //eg "3,42 €"
                    .replace(" ", "")  //eg "3,42€"
                    .replace(",", ".")  //eg "3.42€"
                    .replace("€", "");  //eg "3.42"

            float price = Float.valueOf(priceString);

            Meal meal = new Meal(date, title, type, price);

            meals.add(meal);
        }
        meals.sort(Comparator.comparing(Meal::getDate));
        return meals;
    }

    /** returns all dates in the selectet MensaDateReange
     * @param dateRange MensaDateRange
     * @param doc Docuent to read from
     * @return the Dates
     */
    private static Set<LocalDate> getDates(MensaDateRange dateRange, Document doc) {
        Element timeControls = doc.getElementsByClass("neo-menu-single-controls").first();

        Element timeControl = timeControls
                .getElementsByAttributeValue("href", dateRange.getAttributeCode()).first();

        Set<LocalDate> dates = new HashSet<>();
        if (dateRange.isSingle()) {
            String dateString = timeControl.attr("data-date");
            dates.add(LocalDate.parse(dateString, dateFormatter));
        } else {
            String dateStrings = timeControl.attr("data-range");
            Arrays.stream(dateStrings.split(",")).forEach(datestr ->{
                dates.add(LocalDate.parse(datestr, dateFormatter));
            });
        }
        return dates;
    }
}
