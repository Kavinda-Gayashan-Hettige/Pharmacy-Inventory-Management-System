package service.impl;

import repositiry.ReportsRepository;
import repositiry.impl.ReportsRepositoryImpl;
import service.ReportsService;

public class ReportsServiceImpl implements ReportsService {
    ReportsRepository reportsRepository = new ReportsRepositoryImpl();

}
