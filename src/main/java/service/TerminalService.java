package service;

import model.Terminal;

import java.util.List;
import java.util.Optional;

public interface TerminalService {
    void createTerminal(Terminal terminal);
    Optional<Terminal> getTerminalById(Long id);
    List<Terminal> getAllTerminals();
    void updateTerminal(Terminal terminal);
    void deleteTerminal(Long id);
    void clearAllTerminals();
    List<Terminal> getTerminalsByPosId(Long posId);
    List<Terminal> getTerminalsByMccId(Long mccId);
    boolean validateTerminal(Terminal terminal);
}
