package model;

public final class FriendRoster
{
    private static final Friend[] FRIENDS = {
        new Friend("Double D",
            "resources/images/friends/DoubleD.png",
            QuestionCategory.Procedural),

        new Friend("Jimmy Neutron",
            "resources/images/friends/JimmyNeutron.png",
            QuestionCategory.OOP),

        new Friend("BMO",
            "resources/images/friends/BMO.png",
            QuestionCategory.Functional),

        new Friend("Dexter",
            "resources/images/friends/Dexter.png",
            QuestionCategory.Declarative),

        new Friend("Dr. Doofenshmirtz",
            "resources/images/friends/DrDoofenshmirtz.png",
            QuestionCategory.Imperative),

        new Friend("Nigel Numbuh 1",
            "resources/images/friends/NigelNumbuh1.png",
            QuestionCategory.Imperative,
            QuestionCategory.Declarative),

        new Friend("Morty Smith",
            "resources/images/friends/MortySmith.png",
            QuestionCategory.OOP,
            QuestionCategory.Functional)
    };

    private FriendRoster()
    {
        // Prevent object creation.
    }

    public static Friend[] getFriends()
    {
        return FRIENDS.clone();
    }

    public static Friend getProctor(int questionIndex)
    {
        if(questionIndex < 10)
            return FRIENDS[0]; // Module 1: Double D

        if(questionIndex < 20)
            return FRIENDS[2]; // Module 2: BMO

        if(questionIndex < 30)
            return FRIENDS[1]; // Module 3: Jimmy

        if(questionIndex < 40)
            return FRIENDS[5]; // Module 4: Nigel

        if(questionIndex < 50)
            return FRIENDS[4]; // Module 5: Doofenshmirtz

        if(questionIndex < 60)
            return FRIENDS[3]; // Module 6: Dexter

        if(questionIndex < 70)
            return FRIENDS[6]; // Module 7: Morty

        if(questionIndex < 95)
            return FRIENDS[0]; // Practical A: Double D

        if(questionIndex < 120)
            return FRIENDS[1]; // Practical B: Jimmy

        if(questionIndex < 145)
            return FRIENDS[2]; // Practical C: BMO

        return null;
    }
}