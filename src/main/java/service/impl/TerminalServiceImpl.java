package service.impl;

import main.java.dao.Dao;
import dao.jdbc.TerminalJDBCDaoImpl;
import model.Terminal;
import service.TerminalService;

import java.util.List;
import java.util.Optional;

public class TerminalServiceImpl implements TerminalService {
    private final Dao<Terminal> terminalDao;

    public TerminalServiceImpl() {
        this.terminalDao = new TerminalJDBCDaoImpl();
    }

    @Override
    public void createTerminal(Terminal terminal) {
        validateTerminal(terminal);
        terminalDao.add(terminal);
    }

    @Override
    public Optional<Terminal> getTerminalById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid terminal ID");
        }
        return terminalDao.getById(id);
    }

    @Override
    public List<Terminal> getAllTerminals() {
        return terminalDao.getAll();
    }

    @Override
    public void updateTerminal(Terminal terminal) {
        validateTerminal(terminal);
        terminalDao.update(terminal);
    }

    @Override
    public void deleteTerminal(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid terminal ID");
        }
        terminalDao.deleteById(id);
    }

    @Override
    public void clearAllTerminals() {
        terminalDao.clearTable();
    }

    @Override
    public List<Terminal> getTerminalsByPosId(Long posId) {
        if (posId == null || posId <= 0) {
            throw new IllegalArgumentException("Invalid POS ID");
        }
        return terminalDao.getAll().stream()
                .filter(t -> posId.equals(t.getPosId()))
                .toList();
    }

    @Override
    public List<Terminal> getTerminalsByMccId(Long mccId) {
        if (mccId == null || mccId <= 0) {
            throw new IllegalArgumentException("Invalid MCC ID");
        }
        return terminalDao.getAll().stream()
                .filter(t -> mccId.equals(t.getMccId()))
                .toList();
    }

    @Override
    public boolean validateTerminal(Terminal terminal) {
        if (terminal == null) {
            throw new IllegalArgumentException("Terminal cannot be null");
        }
        if (terminal.getTerminalId() == null || terminal.getTerminalId().isEmpty()) {
            throw new IllegalArgumentException("Terminal ID is required");
        }
        if (terminal.getMccId() == null || terminal.getMccId() <= 0) {
            throw new IllegalArgumentException("Valid MCC ID is required");
        }
        if (terminal.getPosId() == null || terminal.getPosId() <= 0) {
            throw new IllegalArgumentException("Valid POS ID is required");
        }

        // Дополнительная валидация формата terminal_id (9 символов)
        if (!terminal.getTerminalId().matches("^\\d{9}$")) {
            throw new IllegalArgumentException("Terminal ID must be 9 digits");
        }

        return true;
    }
}