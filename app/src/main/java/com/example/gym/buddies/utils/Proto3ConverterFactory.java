package com.example.gym.buddies.utils;
/*
 * Copyright (C) 2013 Square, Inc.
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

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * A {@linkplain Converter.Factory converter} which uses Protocol Buffers 3.
 * <p/>
 * This converter only applies for types which extend from {@link MessageLite} (or one of its
 * subclasses).
 */
public final class Proto3ConverterFactory extends Converter.Factory {
	public static Proto3ConverterFactory create() {
		return new Proto3ConverterFactory(null);
	}

	/**
	 * Create an instance which uses {@code registry} when deserializing.
	 */
	public static Proto3ConverterFactory createWithRegistry(ExtensionRegistryLite registry) {
		return new Proto3ConverterFactory(registry);
	}

	private final ExtensionRegistryLite registry;

	private Proto3ConverterFactory(ExtensionRegistryLite registry) {
		this.registry = registry;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
															Retrofit retrofit) {
		if (!(type instanceof Class<?>)) {
			return null;
		}
		Class<?> c = (Class<?>) type;
		if (!MessageLite.class.isAssignableFrom(c)) {
			return null;
		}

		Parser<MessageLite> parser;
		try {
			Method method = c.getDeclaredMethod("parser");
			//noinspection unchecked
			parser = (Parser<MessageLite>) method.invoke(null);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Found a protobuf message but " + c.getName() + " had no PARSER field.");
		}
		return new ProtoResponseBodyConverter<>(parser, registry);
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type,
														  Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		if (!(type instanceof Class<?>)) {
			return null;
		}
		if (!MessageLite.class.isAssignableFrom((Class<?>) type)) {
			return null;
		}
		return new ProtoRequestBodyConverter<>();
	}


	final class ProtoResponseBodyConverter<T extends MessageLite>
			implements Converter<ResponseBody, T> {
		private final Parser<T> parser;
		private final ExtensionRegistryLite registry;

		ProtoResponseBodyConverter(Parser<T> parser, ExtensionRegistryLite registry) {
			this.parser = parser;
			this.registry = registry;
		}

		@Override
		public T convert(ResponseBody value) throws IOException {
			try {
				return parser.parseFrom(value.byteStream(), registry);
			} catch (InvalidProtocolBufferException e) {
				throw new RuntimeException(e); // Despite extending IOException, this is data mismatch.
			} finally {
				value.close();
			}
		}
	}

	final class ProtoRequestBodyConverter<T extends MessageLite> implements Converter<T, RequestBody> {
		private final MediaType MEDIA_TYPE = MediaType.parse("application/x-protobuf");

		@Override
		public RequestBody convert(T value) throws IOException {
			byte[] bytes = value.toByteArray();
			return RequestBody.create(MEDIA_TYPE, bytes);
		}
	}
}