package net.media.converter.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.media.OpenRtbConverter;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.enums.AdType;
import net.media.openrtb24.request.BidRequest;
import net.media.openrtb24.response.BidResponse;
import net.media.openrtb3.OpenRTB;
import net.media.util.JacksonObjectMapper;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import javax.naming.ConfigurationException;

/**
 * @author shiva.b
 */
public class Response24ToResponse30BenchMark {

  private static final ObjectMapper mapper = JacksonObjectMapper.getMapper();

  @BenchmarkMode(Mode.All)
  @Warmup(time = 2, timeUnit = TimeUnit.SECONDS, iterations = 5)
  @Measurement(time = 2, timeUnit = TimeUnit.SECONDS, iterations = 5)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  @Threads(1)
  @Fork(1)
  @Benchmark
  public void bannerResponse(Blackhole blackhole, ResponseState state) throws OpenRtbConverterException, ConfigurationException, IOException {
    OpenRTB response = mapper.readValue(state.jsonData, OpenRTB.class);
    blackhole.consume(state.openRtbConverter.convert(response, OpenRTB.class,
      BidResponse.class));
  }

  @State(Scope.Benchmark)
  public static class ResponseState {

    public byte[] jsonData;
    public OpenRtbConverter openRtbConverter;

    @Setup(Level.Trial)
    public void doSetup() throws IOException {
      File file = new File("/Users/samya.p/openrtb-converter/converter-bechmark/src/main/resources/banner24Response.json");
      jsonData = Files.readAllBytes(file.toPath());
      Config config = new Config();
      config.setBannerTemplate("");
      config.setAdType(AdType.NATIVE);
      config.setNativeRequestAsString(Boolean.TRUE);
      config.setValidate(false);
      this.openRtbConverter = new OpenRtbConverter(config);
    }
  }
}
