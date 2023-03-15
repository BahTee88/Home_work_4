import java.util.Random;

public class Main {

    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int bossDefaultDamage = bossDamage;
    public static int[] heroesHealth = {270, 260, 250, 300, 400, 250, 250, 300};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 25, 15, 30};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};

    public static int roundNumber = 0;

    static Random random = new Random();
    public static String message = "";

    public static void main(String[] args) {
        printStatistics();
        /* System.out.println(isGameFinished());;*/

        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        message = "";
        chooseBossDefence();
        bossHits();
        heroesHits();
        printStatistics();
        medicPower();
        golemPower();
        luckyPower();
        berserkPower();
        thorpower();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    private static void medicPower() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Medic")) {
                continue;
            } else if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                heroesHealth[i] += 40;
                System.out.println(" Medic healed " + heroesAttackType[i]);
                break;
            }

        }
    }

    private static void golemPower() {
        int damageForGolem = (bossDamage / 5);
        int golemIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Golem")) {
                golemIndex = i;
                break;
            }
        }
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[golemIndex] > 0) {
                heroesHealth[golemIndex] -= damageForGolem;

            } else {
                heroesHealth[golemIndex] = 0;
            }

        }
        System.out.println("Golem took a hit");

    }

    private static void luckyPower() {
        int luckyIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Lucky")) {
                luckyIndex = i;
                break;
            }
        }
        boolean chance = random.nextBoolean();
        if (chance && heroesHealth[luckyIndex] > 0) {
            heroesHealth[luckyIndex] += bossDamage;
            System.out.println("Lucky has a chance to dodge boss attacks");
        }

    }

    private static void berserkPower() {
        int blockDamage = bossDamage / 5;
        int berserkIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Berserk")) {
                berserkIndex = i;
                break;
            }
        }
        if (heroesHealth[berserkIndex] > 0) {
            bossHealth -= blockDamage;
            heroesHealth[berserkIndex] += blockDamage;
            System.out.println("Berserk has blocked damage");
        }
    }

    private static void thorpower() {
        if (bossDamage == 0) {
            bossDamage = bossDefaultDamage;
        }

        boolean chanceThor = random.nextBoolean();
        int thorIndex = 0;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Thor")) {
                thorIndex = i;
            }
        }

        if (chanceThor && heroesHealth[thorIndex] > 0) {
            bossDamage = 0;
            System.out.println("Boss is stub out!");
        }

    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }

    }

    public static void heroesHits() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2;//2,3,4,5,6,7,8,9,10;
                    damage = damage * coefficient;
                    message = "Critical damage: " + damage;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }

    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes Won!!!");
            return true;
        }
        /*if (heroesHealth[0]<=0 && heroesHealth[1]<=0 && heroesHealth[2]<=0){
            System.out.println("Boss Won!!!");
            return true;
        }
        return false;*/

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }

        }
        if (allHeroesDead) {
            System.out.println(" Boss Won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("Round " + roundNumber + "-----------");

        /*String defence;
        if (bossDefence == null){
            defence = "No defence"
        }*/
        System.out.println("Boss health: " + bossHealth + " Damage:" + bossDamage + "  defence:" +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " Damage:" + heroesDamage[i]);
        }
        System.out.println(message);
    }
}