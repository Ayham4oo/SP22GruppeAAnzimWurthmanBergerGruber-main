package com.lol.campusapp.search;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFilter<E> {
    private List<E> allObjects = null;

    /** a setter that can be used to set the Objects that are to be searched
     * does not have to be called, a default will be set by {@code loadAllObjects}-method
     * @param allObjects will be searcht when calling the {@code getSearchResult}-method
     */
    public void setAllObjects(List<E> allObjects) {
        this.allObjects = allObjects;
    }

    /** returns all Objects that match the searchQuery
     * @param searchQuery: if a Object contains information that matches this query it is returned
     * @return all Objects from the database taht match the serachquery
     */
    public List<E> getSearchResult(String searchQuery) {
        if (allObjects == null) {
            allObjects = loadAllObjects();
        }

        List<E> result = allObjects.stream()
                .filter(lectureInstance -> filterPredicate(searchQuery, lectureInstance))
                .collect(Collectors.toList());

        return result;
    }

    /** load the Objects, the filter is supposed to search through
     */
    abstract List<E> loadAllObjects();

    /** Is used to determine if a Object matches a query
     * @param searchQuery query as String
     * @param object to compare with query
     * @return ture if the lectureInstance matches the searchQuery, false otherwise
     */
    private boolean filterPredicate(String searchQuery, E object) {
        String searchString = getSearchString(object);
        return searchString.toLowerCase().contains(searchQuery.toLowerCase());
    }

    /** returns a String that contains all searchable information on the object
     * @param object to search
     * @return String containing object-info
     */
    abstract String getSearchString(E object);
}
