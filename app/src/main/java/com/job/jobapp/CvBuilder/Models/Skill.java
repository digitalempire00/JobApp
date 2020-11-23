package com.job.jobapp.CvBuilder.Models;

public class Skill {
    String skillName;
    int skillRating;

    public Skill() {
    }

    public Skill(String skillName, int skillRating) {
        this.skillName = skillName;
        this.skillRating = skillRating;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(int skillRating) {
        this.skillRating = skillRating;
    }
}
