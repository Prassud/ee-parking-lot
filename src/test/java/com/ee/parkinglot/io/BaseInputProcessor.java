package com.ee.parkinglot.io;

import com.ee.parkinglot.bean.Result;
import com.ee.parkinglot.command.*;
import com.ee.parkinglot.service.ParkingLotService;
import org.junit.Before;
import org.mockito.Mock;

import java.util.Map;

import static com.ee.parkinglot.utils.MessageConstant.*;
import static com.ee.parkinglot.utils.MessageConstant.STATUS_COMMAND_NAME;
import static org.mockito.Matchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class BaseInputProcessor {

	@Mock
	protected CreateCommand createCommand;

	@Mock
	protected ParkCommand parkCommand;

	@Mock
	protected LeaveCommand leaveCommand;
	@Mock
	protected StatusCommand statusCommand;

	@Mock
	protected SearchRNByColorCommand searchRNByColorCommand;

	@Mock
	protected SearchSNByRNCommand searchSNByRNCommand;

	@Mock
	protected SearchSNByColorCommand searchSNByColorCommand1;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	protected void createCommands(Map<String, AbstractCommand> commands) {
		when(createCommand.execute(any())).thenReturn(new Result("success",true));
		when(parkCommand.execute(any())).thenReturn(new Result("success",true));
		when(leaveCommand.execute(any())).thenReturn(new Result("success",true));
		when(searchSNByColorCommand1.execute(any())).thenReturn(new Result("success",true));
		when(searchRNByColorCommand.execute(any())).thenReturn(new Result("success",true));
		when(searchSNByRNCommand.execute(any())).thenReturn(new Result("success",true));
		when(statusCommand.execute(any())).thenReturn(new Result("success",true));

		commands.put(CREATE_COMMAND, createCommand);
		commands.put(PARK_COMMAND_NAME, parkCommand);
		commands.put(LEAVE_COMMAND_NAME, leaveCommand);
		commands.put(SEARCH_BY_COLOR_FOR_SN, searchSNByColorCommand1);
		commands.put(SEARCH_BY_REGISTER_NUMBER, searchRNByColorCommand);
		commands.put(SEARCH_BY_RN_FOR_SN, searchSNByRNCommand);
		commands.put(STATUS_COMMAND_NAME, statusCommand);
	}
}
