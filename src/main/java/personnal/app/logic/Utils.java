package personnal.app.logic;

import personnal.app.logic.utils.ItemType;
import personnal.app.logic.utils.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public static String getStringOfPerson(final Person person) {
        return switch (person) {
            case ARNAUD -> "Arnaud";
            case DARIUS -> "Darius";
            case EMPTY -> "empty";
        };
    }

    public static String getStringOfItemType(ItemType type) {
        return switch (type) {
            case ALIMENTAIRE -> "Alimentaire";
            case MENAGE -> "Ménage";
            case AUTRES -> "Autres";
        };
    }

    public static final Utils.PasswordGenerator IDgenerator =
            new Utils.PasswordGenerator(new Utils.PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true));


    public static final class PasswordGenerator {

        private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String DIGITS = "0123456789";
        private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
        private final boolean useLower;
        private final boolean useUpper;
        private final boolean useDigits;
        private final boolean usePunctuation;

        private PasswordGenerator() {
            throw new UnsupportedOperationException("Empty constructor is not supported.");
        }

        public PasswordGenerator(PasswordGeneratorBuilder builder) {
            this.useLower = builder.useLower;
            this.useUpper = builder.useUpper;
            this.useDigits = builder.useDigits;
            this.usePunctuation = builder.usePunctuation;
        }

        public static class PasswordGeneratorBuilder {

            private boolean useLower;
            private boolean useUpper;
            private boolean useDigits;
            private boolean usePunctuation;

            public PasswordGeneratorBuilder() {
                this.useLower = false;
                this.useUpper = false;
                this.useDigits = false;
                this.usePunctuation = false;
            }

            public PasswordGeneratorBuilder useLower(boolean useLower) {
                this.useLower = useLower;
                return this;
            }

            public PasswordGeneratorBuilder useUpper(boolean useUpper) {
                this.useUpper = useUpper;
                return this;
            }

            public PasswordGeneratorBuilder useDigits(boolean useDigits) {
                this.useDigits = useDigits;
                return this;
            }

            public PasswordGeneratorBuilder usePunctuation(boolean usePunctuation) {
                this.usePunctuation = usePunctuation;
                return this;
            }

            public PasswordGenerator build() {
                return new PasswordGenerator(this);
            }
        }

        public String generate(int length) {
            if (length <= 0)
                return "";

            StringBuilder password = new StringBuilder(length);
            Random random = new Random(System.nanoTime());

            List<String> charCategories = new ArrayList<>(4);
            if (useLower)
                charCategories.add(LOWER);
            if (useUpper)
                charCategories.add(UPPER);
            if (useDigits)
                charCategories.add(DIGITS);
            if (usePunctuation)
                charCategories.add(PUNCTUATION);

            for (int i = 0; i < length; i++) {
                String charCategory = charCategories.get(random.nextInt(charCategories.size()));
                int position = random.nextInt(charCategory.length());
                password.append(charCategory.charAt(position));
            }

            return new String(password);
        }
    }
}
