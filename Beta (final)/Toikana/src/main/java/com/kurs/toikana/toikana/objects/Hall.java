package com.kurs.toikana.toikana.objects;

public class Hall {
    String name;
    String tablesNumber;
    String takenTables;
    String arrangementForm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTablesNumber() {
        return tablesNumber;
    }

    public void setTablesNumber(String tablesNumber) {
        this.tablesNumber = tablesNumber;
    }
    public String getTakenTables() {
        return takenTables;
    }

    public void setTakenTables(String takenTables) {
        this.takenTables = takenTables;
    }
    public String getArrangementForm() {
        return arrangementForm;
    }

    public void setArrangementForm(String arrangementForm) {
        this.arrangementForm = arrangementForm;
    }
    public Hall(String name, String tablesNumber, String takenTables, String arrangementForm){
        this.name = name;
        this.tablesNumber = tablesNumber;
        this.takenTables = takenTables;
        this.arrangementForm = arrangementForm;
    }
}
