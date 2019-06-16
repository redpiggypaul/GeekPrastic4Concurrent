package geekJavaConCodePrastic.ooThinkSet_12;

public class DBPush {

        private volatile static DBPush dbPush = null;

        private DBPush() {
        }

        public static DBPush getInStance() {
            if (dbPush == null) {
                synchronized (DBPush.class) {
                    if (dbPush == null) {
                        dbPush = new DBPush();
                    }
                }
            }
            return dbPush;
        }

}
