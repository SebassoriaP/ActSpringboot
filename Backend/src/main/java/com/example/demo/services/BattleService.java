package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.model.Pokemon;

@Service
public class BattleService {

    public Pokemon fight(Pokemon p1, Pokemon p2) {
        int score1 = p1.getHp() + p1.getAttack() + p1.getDefense();
        int score2 = p2.getHp() + p2.getAttack() + p2.getDefense();

        if (score1 >= score2) {
            return p1;
        }
        return p2;
    }
}