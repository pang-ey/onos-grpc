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
package com.winlab.panggrpcserver;

import com.google.common.collect.ImmutableSet;
import org.onosproject.cfg.ComponentConfigService;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Dictionary;
import java.util.Properties;
import java.io.IOException;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

//import com.winlab.grpcserver.PGrpcServer;
//import com.winlab.grpcserver.Reply;
//import com.winlab.grpcserver.Request;
import com.winlab.panggrpcserver.PGrpcServer;

@Component(immediate = true)
public class AppComponent {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private PangGrpcServer server;

    @Activate
    protected void activate() {
        try {
            server = new PangGrpcServer();
//            server.start();
        } catch (IOException e) {
            log.warn("Failed to start grpc server: {}", e);
        }

        log.info("Pang grpc server Started");
    }

    @Deactivate
    protected void deactivate() {
//        server.stop();
        log.info("Stopped");
    }

    private class PangGrpcServer {
        private int port = 2222;
        private Server server;

        private void start() throws IOException {
            server = NettyServerBuilder.forPort(port)
//                    .addService(new PangGrpcServerImpl())
                    .build()
                    .start();

            log.info("server started");
        }

        private void stop() {
            if (server != null) {
                server.shutdown();
            }
        }

        private void blockUntilShutdown() throws InterruptedException {
            if (server != null) {
                server.awaitTermination();
            }
        }

//        private class PGrpcServerImpl extends PGrpcServer.PGrpcServerImplBase {
//            public void evpnRoute(Request req, StreamObserver<Reply> responseObserver) {
//                log.info("Request: {}", req.getName());
//                Reply reply = Reply.newBuilder().setMessage(("Hello: " + req.getName())).build();
//                responseObserver.onNext(reply);
//                responseObserver.onCompleted();
//            }
//        }
    }
}
