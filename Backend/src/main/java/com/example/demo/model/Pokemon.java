package com.example.demo.model;

import java.util.List;

public class Pokemon {
    private String id;
    private String name;
    private String type;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private List<String> abilities;
    private List<String> powers;
    private String imageUrl;

    public Pokemon() {}

    public Pokemon(String id, String name, String type, int hp, int attack, int defense,
                   List<String> abilities, List<String> powers, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.abilities = abilities;
        this.powers = powers;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }

    public List<String> getPowers() { return powers; }
    public void setPowers(List<String> powers) { this.powers = powers; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}