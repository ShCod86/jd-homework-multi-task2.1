import java.util.*;

public class RobotMap {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(
            new Thread(() -> {
                Integer result = calculation(generateRoute("RLRFR", 100));
                synchronized (sizeToFreq) {
                    addToMap(sizeToFreq, result);
                    }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        print(sizeToFreq);
    }
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
    public static Integer calculation (String string) {
        Integer count = 0;
        char[] chars = string.toCharArray();
        for (Character ch : chars) {
            if(ch.equals('R'))
                count++;
        }
        return count;
    }
    public static void addToMap(Map<Integer, Integer> map, Integer key) {
        if(map.containsKey(key))
            map.put(key, map.get(key) + 1);
        else
            map.put(key, 1);
        
    }
    public static void print(Map<Integer, Integer> map) {
        Map.Entry<Integer, Integer> maxEntry = map.entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElse(null);
        System.out.printf( "Самое частое количество повторений %d (%d раз)\n", maxEntry.getKey(), maxEntry.getValue());
        System.out.println("Другие размеры: ");
        map.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(maxEntry.getKey()))
                .forEach(entry -> System.out.printf("- %d (%d раз)\n", entry.getKey(), entry.getValue()));

    }
}
