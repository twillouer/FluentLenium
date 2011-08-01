package fr.java.freelance.fluentlenium.unit;


import com.google.common.collect.Lists;
import fr.java.freelance.fluentlenium.domain.FluentList;
import fr.java.freelance.fluentlenium.domain.FluentWebElement;
import fr.java.freelance.fluentlenium.filter.Filter;
import fr.java.freelance.fluentlenium.filter.matcher.Matcher;
import fr.java.freelance.fluentlenium.search.Search;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FluentListSearch {
    @Mock
    Search search;

    FluentList<FluentWebElement> fluentList;

    @Mock
    Filter filter1;

    @Mock
    Matcher matcher1;

    @Mock
    Filter filter2;
    @Mock
    WebElement webElement;

    List<FluentWebElement> webElements;

    FluentWebElement fluentWebElement;

    @Before
    public void before() throws IntrospectionException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        webElements = new ArrayList<FluentWebElement>();
        fluentWebElement = new FluentWebElement(webElement);
        webElements.add(fluentWebElement);
        Field field = fluentWebElement.getClass().getDeclaredField("search");
        field.setAccessible(true);
        field.set(fluentWebElement, search);


        fluentList = new FluentList(webElements);
    }

    @Test
    public void findElementsIsSearched() {
        String name = "cssStyle";
        FluentList fluentList1 = new FluentList(webElements);
        when(search.find(name, null)).thenReturn(fluentList1);
        FluentList fluentListResponse = fluentList.find(name, null);
        assertThat(fluentListResponse).hasSize(1);
    }

    @Test
    public void findElementByPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentList(webElements));
        FluentWebElement fluentWebElement = fluentList.find(name, 0, null);
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void ShouldThrowAnErrorWhenWrongPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentList(webElements));
        FluentWebElement fluentWebElement = fluentList.find(name, 1, null);
    }

    @Test
    public void findFirstElement() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentList(webElements));
        FluentWebElement fluentWebElement = fluentList.findFirst(name, null);
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnErrorWhenNoFirstPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentList(Lists.newArrayList()));
        FluentWebElement fluentWebElement = fluentList.findFirst(name, null);
    }

}
