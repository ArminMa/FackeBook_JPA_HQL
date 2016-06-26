/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kth.HI1034;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.XmlRootElement;

@RestController
public class PingController {

	@RequestMapping("/")
	public Hello hello() {

		return new Hello("Hello Worls!");
	}

	@XmlRootElement
	public class Hello{
		String helloPing;

		public Hello() {
		}

		public Hello(String helloPing) {
			this.helloPing = helloPing;
		}

		public String getHelloPing() {
			return helloPing;
		}

		public void setHelloPing(String helloPing) {
			this.helloPing = helloPing;
		}
	}

}
