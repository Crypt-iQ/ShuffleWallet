/*
 * Copyright by the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycelium;

import com.mycelium.fundsIn.AddMasterPrivController;
import com.mycelium.fundsIn.AddPrivKeyinWIFController;
import com.mycelium.fundsIn.AddReceiveAddressController;
import com.mycelium.fundsIn.AddUTXOController;
import com.mycelium.fundsOut.ToExtAddressController;
import com.mycelium.fundsOut.ToHDAddressesController;
import com.mycelium.fundsOut.ToMasterPubController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.controller.injection.scopes.FlowScoped;
import io.datafx.controller.util.VetoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javax.annotation.PostConstruct;

@FlowScoped
@ViewController("shuffle_start.fxml")
public class ShuffleStartController {
    @FXML
    private Button AddBtn;
    @FXML
    @ActionTrigger("cancel")
    private Button cancelBtn;
    @ViewNode
    private Button nextBtn;
    public Main.OverlayUI overlayUI;
    @FXML
    @ViewNode
    private RadioButton fundsInReceiveAddress;
    @FXML
    @ViewNode
    private RadioButton fundsInPrivKeyWIF;
    @FXML
    @ViewNode
    private RadioButton fundsInMasterPrivKey;
    @FXML
    @ViewNode
    private RadioButton fundsInUTXOs;
    @FXML
    @ViewNode
    private RadioButton fundsOutInternalHD;
    @FXML
    @ViewNode
    private RadioButton fundsOutExtAddresses;
    @FXML
    @ViewNode
    private RadioButton fundsOutXPub;
    @FXML
    @ViewNode
    private RadioButton connectByIP;
    @FXML
    @ViewNode
    private RadioButton connectByFetch;
    @FXML
    @ViewNode
    private ToggleGroup shuffleInOptions;
    @FXML
    @ViewNode
    private ToggleGroup shuffleOutOptions;
    @FXML
    @ViewNode
    private ToggleGroup shuffleConnectOptions;


    @ActionHandler
    FlowActionHandler flowActionHandler;

    @PostConstruct
    public void initialize() {
        // most is injected by fxml already
        // setUserData for button selection FundsIn
        fundsInReceiveAddress.setUserData(AddReceiveAddressController.class);
        fundsInPrivKeyWIF.setUserData(AddPrivKeyinWIFController.class);
        fundsInMasterPrivKey.setUserData(AddMasterPrivController.class);
        fundsInUTXOs.setUserData(AddUTXOController.class);

        // setUserData for button selection FundsOut
        fundsOutInternalHD.setUserData(ToHDAddressesController.class);
        fundsOutExtAddresses.setUserData(ToExtAddressController.class);
        fundsOutXPub.setUserData(ToMasterPubController.class);

        // setUserData for button selection connectOptions
        connectByIP.setUserData("connectByIP");
        connectByFetch.setUserData("connectByFetch");
    }

    @ActionMethod("cancel")
    public void cancel() {
        try {
            flowActionHandler.navigate(MainController.class);
        } catch (VetoException | FlowException e) {
            e.printStackTrace();
        }
    }

    public Class getFundsInClass() {
        return (Class) shuffleInOptions.getSelectedToggle().getUserData();
    }

    public ToggleGroup getFundsOutGroup() {
        return shuffleOutOptions;
    }

    public void goToFundsOut() {
        try {
            flowActionHandler.navigate((Class<?>) shuffleOutOptions.getSelectedToggle().getUserData());
        } catch (VetoException | FlowException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnectingManual() {
        return "connectByIP".equals(shuffleConnectOptions.getSelectedToggle().getUserData());
    }

    @ActionMethod("next")
    public void next(ActionEvent actionEvent) {
        // if next is clicked and every group has a selection made
        //todo: setter method for each group
        String selectedToggle = "" + shuffleInOptions.getSelectedToggle().getUserData();
        System.out.println(selectedToggle);

        //Main.OverlayUI<com.mycelium.fundsIn.AddReceiveAddressController> screen = Main.instance.overlayUI("fundsOut/shuffle_toMasterPub.fxml");
        //Main.OverlayUI<com.mycelium.fundsIn.AddReceiveAddressController> screen = Main.instance.overlayUI("fundsIn/shuffle_"+selectedToggle+".fxml");
        try {
            flowActionHandler.navigate((Class<?>) shuffleInOptions.getSelectedToggle().getUserData());
        } catch (VetoException | FlowException e) {
            e.printStackTrace();
        }
    }
}
