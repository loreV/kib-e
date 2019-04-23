package org.kibe.onboard.communication.board;

public interface DataEntrySubject {
    void registerListener(DataListener dataListener);
    void unregister(DataListener dataListener);
    void notifyListeners();
}
