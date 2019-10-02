/*
 * Copyright 2019-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pang;

import com.google.common.collect.ImmutableSet;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import pang.OnosRequest;

import static org.onlab.util.Tools.get;

@Component(immediate = true)
public class AppComponent {

    private final Logger log = LoggerFactory.getLogger(getClass());

    OnosServer server;

    @Activate
    protected void activate() {
        log.info("activate");

        try {
            server = new OnosServer();
            server.start();
            //server.blockUntilShutdown();
        } catch (IOException e) {
            log.warn("Failed to start gRPC server: {}", e);
        }

        log.info("Started");
    }

    @Deactivate
    protected void deactivate() {
        log.info("Stopped");
    }

    private class OnosServer {

        private int port = 50051;
        private Server server;

        private void start() throws IOException {
            log.info("OnosServer start");

            server = NettyServerBuilder.forPort(port)
                    .addService(new OnosServerImpl())
                    .build()
                    .start();

            log.info("Server started, listening on " + port);
        }

        private void stop() {
            if (server != null) {
                server.shutdown();
            }
        }

        /**
         * Await termination on the main thread since the grpc library uses daemon threads.
         */
        private void blockUntilShutdown() throws InterruptedException {
            if (server != null) {
                server.awaitTermination();
            }
        }


        private class OnosServerImpl extends OnosServerGrpc.OnosServerImplBase {
            public void evpnRoute(OnosRequest req, StreamObserver<OnosReply> responseObserver) {
                log.info("Request: {}", req.getMessage());
                OnosReply reply = OnosReply.newBuilder().setMessage(("Hello: " + req.getMessage())).build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
        }
    }
}
