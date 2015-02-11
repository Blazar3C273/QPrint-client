/**
 */
public enum JobState {
    /**
     */
    FAILED,

    /**
     */
    COMPLETED,

    /**
     */
    IN_PROGRESS, PAID;

    public static JobState parceString(String string) {
        switch (string) {
            case "paid":
                return JobState.PAID;
            case "failed":
                return JobState.FAILED;
            case "inProgress":
                return JobState.IN_PROGRESS;
            case "completed":
                return JobState.COMPLETED;
        }
        return JobState.FAILED;
    }
}

