package main_Class;

import java.util.Locale;

import server_Models.Configuration;
import server_Models.Translator;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * The singleton instance of this class provide central storage for resources
 * used by the program. It also defines application-global constants, such as
 * the application name.
 * 
 * Changed applicationname and added few more Locales by Michael Tu
 * 
 * @author Brad Richards
 * 
 */
public class ServiceLocator {
    private static ServiceLocator serviceLocator; // singleton

    // Application-global constants
    final private Class<?> DOMINION_CLASS = Main.class;
    final private String DOMINION_NAME = "Main";
    
    
    // Supported locales (for translations)
    final private Locale[] locales = new Locale[] { new Locale("de"), new Locale("en"),  new Locale("se"),  new Locale("ch") };

    // Resources
    private Configuration configuration;
    private Translator translator;

    /**
     * Factory method for returning the singleton
     * @param mainClass The main class of this program
     * @return The singleton resource locator
     */
    public static ServiceLocator getServiceLocator() {
        if (serviceLocator == null)
            serviceLocator = new ServiceLocator();
        return serviceLocator;
    }

    /**
     * Private constructor, because this class is a singleton
     * @param appName Name of the main class of this program
     */
    private ServiceLocator() {
        // Currently nothing to do here. We must define this constructor anyway,
        // because the default constructor is public
    }

    public Class<?> getDOMINION_CLASS() {
        return DOMINION_CLASS;
    }
    
    public String getDOMINION_NAME() {
        return DOMINION_NAME;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Locale[] getLocales() {
        return locales;
    }

    public Translator getTranslator() {
        return translator;
    }
    
    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

}
